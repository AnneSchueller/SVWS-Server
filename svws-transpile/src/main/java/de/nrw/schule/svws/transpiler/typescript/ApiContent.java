package de.nrw.schule.svws.transpiler.typescript;

import java.util.HashMap;
import java.util.Map;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.Tree.Kind;

import de.nrw.schule.svws.transpiler.Transpiler;
import de.nrw.schule.svws.transpiler.TranspilerException;
import de.nrw.schule.svws.transpiler.TranspilerUnit;

/**
 * Diese Klasse repräsentiert einen möglichen Content für einen HTTP-Request oder 
 * eine HTTP-Response bei einer OpenAPI-Methode.
 */
public class ApiContent {

	/** Der Mime-Type */
	public final ApiMimeType mimetype;

	/** Der Typescript-Datentyp */
	public final String datatype;
	
	/** Gibt an, ob der Inhalt ein JSON-Array-basierter Datentyp ist */
	public final boolean isArrayType;

	/** Der Typescript-Datentyp */
	public final String arrayElementType;
	
	/** a non final temporary attribute used for the determining the final value */
	private String tmpArrayElementType = null;
		
	/** Ein Vektor mit den benötigten Klassen bei den Typescript-Importen */
	public final HashMap<String, String> importsRequired = new HashMap<>();


	/**
	 * Erstellt anhand der übergebenen Annotation und mithilfe des übergebenen Transpilers ein
	 * neues {@link ApiContent}-Objekt.
	 *   
	 * @param transpiler   der Transpiler
	 * @param annotation   das {@link AnnotationTree} für die {@link io.swagger.v3.oas.annotations.media.Content}-Annotation
	 */
	ApiContent(Transpiler transpiler, AnnotationTree annotation) {
		Map<String, ExpressionTree> args = transpiler.getArguments(annotation);
		mimetype = determineMimetype(args);
		if (mimetype == ApiMimeType.MULTIPART_FORM_DATA) {
			datatype = "FormData";
			isArrayType = false;
			arrayElementType = null;
		} else {
			datatype = determineImplementationType(transpiler, args);
			isArrayType = tmpArrayElementType != null;
			arrayElementType = tmpArrayElementType;
		}
	}
	
	private static ApiMimeType determineMimetype(Map<String, ExpressionTree> args) {
		ExpressionTree value = args.get("mediaType");
        if(value == null) {
            throw new TranspilerException("Transpiler Exception: Missing mediaType value for @Content annotation.");
        }
		if ((value.getKind() == Kind.STRING_LITERAL) && (value instanceof LiteralTree literal) && (literal.getValue() instanceof String str))
			return ApiMimeType.get(str);
		if (value.getKind() == Kind.MEMBER_SELECT)
			return ApiMimeType.get(value.toString());
		throw new TranspilerException("Transpiler Exception: Unhandled mediaType argument for Content annotation.");
	}

	private String determineImplementationType(Transpiler transpiler, Map<String, ExpressionTree> args) {
		ExpressionTree value = args.get("array");
		if (value != null) {
			if (value instanceof AnnotationTree att) {
				if (!transpiler.isAnnotationType("io.swagger.v3.oas.annotations.media.ArraySchema", att))
					throw new TranspilerException("Transpiler Exception: Unhandled annotation type used in Content annotation.");
				Map<String, ExpressionTree> arrayArgs = transpiler.getArguments(att);
				importsRequired.put("List", "java.util");
				importsRequired.put("Vector", "java.util");
				tmpArrayElementType = determineImplementationType(transpiler, arrayArgs);
				return "List<" + tmpArrayElementType + ">";
			}
			throw new TranspilerException("Transpiler Exception: Unhandled array argument for Content annotation.");					
		}
				
		value = args.get("schema");
		if (value != null) {
			if (value instanceof AnnotationTree att) {
				if (!transpiler.isAnnotationType("io.swagger.v3.oas.annotations.media.Schema", att))
					throw new TranspilerException("Transpiler Exception: Unhandled annotation type used in Content annotation.");
				Map<String, ExpressionTree> schemaArgs = transpiler.getArguments(att);
				ExpressionTree expr = schemaArgs.get("implementation");
				if (expr instanceof MemberSelectTree mst) {
					if (!"class".equals(mst.getIdentifier().toString()))
						throw new TranspilerException("Transpiler Exception: Unhandled member select for implementation argument of Schema annotation.");
					String implType = mst.getExpression().toString();
					TranspilerUnit tu = transpiler.getTranspilerUnit(mst);
					importsRequired.put(implType, tu.imports.get(implType));
					return implType;
				}
				expr = schemaArgs.get("format");
				if (expr instanceof LiteralTree lt) {
					if ((lt.getKind() == Kind.STRING_LITERAL) && "binary".equals(lt.getValue()))
						return "Blob";
				}
				throw new TranspilerException("Transpiler Exception: Unhandled implementation argument for Schema annotation.");
			}
			throw new TranspilerException("Transpiler Exception: Unhandled schema argument for Content annotation.");					
		}
		throw new TranspilerException("Transpiler Exception: Unhandled implementation argument for Content annotation.");					
	}
	
}
