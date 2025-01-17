package de.nrw.schule.svws.transpiler;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import de.nrw.schule.svws.core.adt.collection.LinkedCollection;


/**
 * This class provides a transpiler for transpiling java source code
 * into another programming language that is supported by implementing
 * the {@link TranspilerLanguagePlugin} class. Therefore the
 * Java Compiler API (module java.compiler) and its abstract syntax tress 
 * (AST, module jdk.compiler) are used.
 */
@SupportedSourceVersion( SourceVersion.RELEASE_17 )
@SupportedAnnotationTypes( "*" )
public class Transpiler extends AbstractProcessor {

	/// Java input and compiler attributes
	
	/** All Java files used for the transpiler input */
	private final Iterable<? extends JavaFileObject> javaFiles;
	
	/** The reference to the compiler instance used by thsi transpiler */
	private final JavaCompiler compiler;
	
	/** The diagnostics collector of the java compiler to report infos, warnings and erros regarding the Java source code */
	private final DiagnosticCollector<JavaFileObject> diagnostics;
	
	/** The file manager für the Java source files */
	private final StandardJavaFileManager javaFileManager;
	
	/** The scanner util for scanning the Java source code while compiling */
	private final TranspilerJavaScanner scanner = new TranspilerJavaScanner();
	
	/** The directory where to place the compiler class output files */
	private String classdir;
	
	/** A temporary variable used by the annotation processor for accessing the Tree-API of the java compiler  */
    private Trees trees;
    
    /** A reference to the element utils of the java compiler processing environment */
    private Types typeUtils;

    /** A reference to the element utils of the java compiler processing environment */
    private Elements elementUtils;

    
    /// Transpiler State attributes
    
	/** a map containing a mapping between a java compiler abstract syntax tree node ant the corresponding tree path object */
	private final HashMap<Tree, TreePath> mapTreePath = new HashMap<>();
	
	/** a list containing all classes to be transpiled */
	private final ArrayList<ClassTree> allClasses = new ArrayList<>();
	
	/** a map containing the set of modifier to java compiler abstract syntax tree nodes */
	private final HashMap<Tree, Set<Modifier>> allModifier = new HashMap<>();

	/** a map containing all transpiler units associated with their corresponding compilation unit */
	private final HashMap<CompilationUnitTree, TranspilerUnit> mapUnits = new HashMap<>();
	
	/** a map containing the package name to a map with the transpiler units of this package associated with the class name */
	private final HashMap<String, HashMap<String, TranspilerUnit>> mapUnitsByPackage = new HashMap<>();
	

	/// Transpiler Languages

	/** a list of transpiler language plugins*/
	private final ArrayList<TranspilerLanguagePlugin> languagePlugins = new ArrayList<>();
	
	
	
    /**
     * Creates a new transpiler object for transpiling the specified java sources
     * 
     * @param classdir   the directory where to place the compiler class output files
     * @param sources    the {@link File}-Objects for all Java-Sources
     */
	public Transpiler(String classdir, File... sources) {
		compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<>();
		javaFileManager = compiler.getStandardFileManager(diagnostics, null, null);
		javaFiles = javaFileManager.getJavaFileObjects(sources);
		this.classdir = classdir;
	}

	
	/**
	 * Prints the paths of all Java input files on the command line.  
	 */
	public void printSourceFiles() {
		System.out.println("Input Files: ");
		for (JavaFileObject javaFile : javaFiles) {
			System.out.println("  -> " + javaFile.getKind() + " " + javaFile.getName());
		}
	}
	
	
	/**
	 * Starts the compilation process for all Java sources passed to the transpiler and
	 * aggregates information about the Java sources for the transpiling process.
	 */
	public void transpile() {
		System.out.println("Running Transpiler...");
		Vector<String> options = new Vector<>();
		options.add("-encoding");
		options.add("UTF-8");
		if (classdir != null) {
			options.add("-d");
			options.add(classdir);
		}
		CompilationTask compilationTask = compiler.getTask(null, javaFileManager, diagnostics, options, null, javaFiles);
		compilationTask.setProcessors(Arrays.asList(this));
		if (!compilationTask.call()) {
			System.out.println("Fehler beim Compilieren des Java-Codes!");
			// TODO further reactions on compiler errors
	        List<Diagnostic<? extends JavaFileObject>> diags = diagnostics.getDiagnostics();
	        for (Diagnostic<? extends JavaFileObject> diag : diags) {
	            System.err.println(diag.getMessage(null));
	        }			
		}
	}
	
	
	/**
	 * Add a language plugin to this transpiler for transpiling into a specific language
	 *  
	 * @param plugin   the language plugin for the transpiler
	 */
	public void addLanguagePlugin(TranspilerLanguagePlugin plugin) {
		if (plugin == null)
			throw new TranspilerException("Transpiler Error: null cannot be added as a transpiler language plugin");
		languagePlugins.add(plugin);
	}
	

	
	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering the 
	 * tree node.
	 * 
	 * @param path   the tree path object to be registered for the tree node. 
	 * @param node   the tree node
	 */
	void visitTree(TreePath path, Tree node) {
		mapTreePath.put(node, path);
		getTranspilerUnit(path).mapTreePath.put(node, path);
	}
	
	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering 
	 * expressions.
	 * 
	 * @param path   the tree path object of the expression tree node. 
	 * @param node   the expression tree node
	 */
	void visitExpressionTree(TreePath path, ExpressionTree node) {
		// ensure, that method invocation parameters are in the expression list before the method identifier
		if (node instanceof MethodInvocationTree miTree)
			getTranspilerUnit(path).allExpressions.addAll(miTree.getArguments());
		// add the current expression
		getTranspilerUnit(path).allExpressions.add(node);
	}
	
	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering 
	 * modifiers on the parent of the modifier tree node.
	 * 
	 * @param path   the tree path object of the modifier tree node. 
	 * @param node   the modifier tree node
	 */
	void visitModifierTree(TreePath path, ModifiersTree node) {
		allModifier.put(path.getParentPath().getLeaf(), node.getFlags());
	}
	
	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering
	 * a class for transpilation. 
	 * 
	 * @param path   the tree path of the tree node. 
	 * @param node   the class tree node  
	 */
	void visitClassTree(TreePath path, ClassTree node) {
		allClasses.add(node);
	}

	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering
	 * an identifier. 
	 * 
	 * @param path   the tree path of the tree node. 
	 * @param node   the class tree node  
	 */
	void visitIdentifierTree(TreePath path, IdentifierTree node) {
		getTranspilerUnit(path).allIdentifier.add(new AbstractMap.SimpleEntry<>(node, path));
	}
	
	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering
	 * an annotation. 
	 * 
	 * @param path   the tree path of the tree node. 
	 * @param node   the annotation tree node  
	 */
	void visitAnnotation(TreePath path, AnnotationTree node) {
		Element elem = trees.getElement(path);
		if ((elem.getKind() == ElementKind.ANNOTATION_TYPE) && (elem instanceof TypeElement te))
			getTranspilerUnit(path).annotations.put(te.getSimpleName().toString(), elementUtils.getPackageOf(te).getQualifiedName().toString());
	}

	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering
	 * a method identifier.
	 * 
	 * @param path   the tree path of the tree node 
	 * @param node   the method tree node  
	 */
	void visitMethodTree(TreePath path, MethodTree node) {
		Tree parent = path.getParentPath().getLeaf();
		if (parent == null)
			return;
		Set<Tree> scopes = getTranspilerUnit(path).allLocalMethods.get(node.getName().toString());
		if (scopes == null) {
			scopes = new HashSet<>();
			scopes.add(parent);
			getTranspilerUnit(path).allLocalMethods.put("" + node.getName(), scopes);
		} else {
			scopes.add(parent);			
		}
		Map<String, MethodTree> methods = getTranspilerUnit(path).allLocalMethodsByScope.get(parent);
		if (methods == null) {
			methods = new HashMap<>();
			getTranspilerUnit(path).allLocalMethodsByScope.put(parent, methods);
		}
		methods.put("" + node.getName(), node);
	}
	

	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering
	 * a variable identifier. 
	 * 
	 * @param path   the tree path of the tree node 
	 * @param node   the variable tree node  
	 */
	void visitVariableTree(TreePath path, VariableTree node) {
		Tree parent = path.getParentPath().getLeaf();
		if (parent == null)
			return;
		Set<Tree> scopes = getTranspilerUnit(path).allLocalVariables.get("" + node.getName());
		if (scopes == null) {
			scopes = new HashSet<>();
			scopes.add(parent);
			getTranspilerUnit(path).allLocalVariables.put("" + node.getName(), scopes);
		} else {
			scopes.add(parent);
		}
		Map<String, VariableTree> vars = getTranspilerUnit(path).allLocalVariablesByScope.get(parent);
		if (vars == null) {
			vars = new HashMap<>();
			getTranspilerUnit(path).allLocalVariablesByScope.put(parent, vars);
		}
		vars.put("" + node.getName(), node);
	}
	
	
	/**
	 * This method is called by the {@link TranspilerJavaScanner} for registering
	 * a type parameter identifier. 
	 * 
	 * @param path   the tree path of the tree node 
	 * @param node   the variable tree node  
	 */
	void visitTypeParameter(TreePath path, TypeParameterTree node) {
		getTranspilerUnit(path).typeParameters.add(node.getName().toString());
	}
	
	
	/**
	 * Returns the type utility class from the java compiler (see {@link Types}).
	 *  
	 * @return the type utility class
	 */
	public Types getTypeUtils() {
		return typeUtils;
	}
	
	
	/**
	 * Returns all classes to be transpiled - nested and non nested classes.
	 * 
	 * @return a list containing all classes to be transpiled
	 */
	public List<ClassTree> getAllClasses() {
		return allClasses;
	}
	
	
	/**
	 * Returns the transpiler unit for the specified tree path.
	 * 
	 * @param path   the tree path
	 * 
	 * @return the transpiler unit
	 */
	private TranspilerUnit getTranspilerUnit(TreePath path) {
		TranspilerUnit unit = mapUnits.get(path.getCompilationUnit());
		if (unit == null)
			throw new TranspilerException("Transpiler Error: Cannot access teh requested transpiler unit for the tree path");
		return unit;
	}
	
	
	/**
	 * Returns the transpiler unit for the specified tree node.
	 * 
	 * @param node   the tree node
	 * 
	 * @return the transpiler unit
	 */
	public TranspilerUnit getTranspilerUnit(Tree node) {
		return mapUnits.get(getTreePath(node).getCompilationUnit());
	}

	
	
	/**
	 * Returns the tree path for the specified element or null if the 
	 * element ist not found
	 * 
	 * @param element   the element
	 * 
	 * @return the tree path or null 
	 */
	public TreePath getTreePath(Element element) {
		return trees.getPath(element);
	}
	
	

	/**
	 * Returns the tree path object for the specified tree node.
	 * 
	 * @param node   the tree node
	 * 
	 * @return the tree path object of the node
	 */
	private TreePath getTreePath(Tree node) {
		TreePath path = mapTreePath.get(node);
		if (path == null) {
			for (CompilationUnitTree unit : mapUnits.keySet()) {
				path = trees.getPath(unit, node);
				if (path != null)
					return path;
			}
			throw new TranspilerException("Transpiler Error: Tree was not visited before - unkown tree path.");
		}
		return path;
	}


	/**
	 * Returns the tree path object of the parent tree node.
	 *  
	 * @param node   the tree node whose parent tree path should be retrieved
	 * 
	 * @return the tree path object of the parent tree node
	 */
	private TreePath getParentTreePath(Tree node) {
		TreePath path = getTreePath(node).getParentPath();
		if (path == null)
			throw new TranspilerException("Transpiler Error: Parent tree was not visited before - unkown tree path.");
		return path;
	}
	
	
	/**
	 * Returns the parent tree object for the specified tree object
	 * 
	 * @param node   the tree object
	 * 
	 * @return the parent tree object
	 */
	public Tree getParent(Tree node) {
		return getParentTreePath(node).getLeaf();
	}
	
	
	/**
	 * Checks whether the specified class tree node is a nested class or not. 
	 * 
	 * @param node   the class tree node
	 * 
	 * @return true if the class is nested and false otherwise
	 */
	public boolean isNested(ClassTree node) {
		Tree parent = getParent(node);
		return (parent != null) && (parent.getKind() != Tree.Kind.COMPILATION_UNIT);
	}
	

	/**
	 * Returns whether a transpiler unit exists for the class represented by
	 * the specified package and class name.  
	 *  
	 * @param packageName   the package name
	 * @param className     the class  name
	 * 
	 * @return true if the class exists and false otherwise
	 */
	public boolean hasTranspilerUnit(String packageName, String className) {
		HashMap<String, TranspilerUnit> listUnits = mapUnitsByPackage.get(packageName);
		if (listUnits == null)
			return false;
		TranspilerUnit tu = listUnits.get(className);
		return (tu != null);
	}
	
	
	/**
	 * Returns the full class name of the specified class tree, i.e. with
	 * the package and in case of a nested class with its parent class(es).
	 * 
	 * @param node   the class tree node
	 * 
	 * @return the full class name
	 */
	public String getFullClassName(ClassTree node) {
		boolean isNested = isNested(node);
		Tree parent = getParent(node);
		if (isNested) {
			if (!(parent instanceof ClassTree))
				throw new TranspilerException("Transpiler Error: Nested class are only supported inside other classes.");
			return getFullClassName((ClassTree) parent) + "." + node.getSimpleName();
		} 
		return getTreePath(node).getCompilationUnit().getPackageName() + "." + node.getSimpleName();		
	}

	
	/**
	 * Returns the list of attributes of the class tree node
	 *  
	 * @param node   the class tree node
	 * 
	 * @return the list of attributes of the class tree node
	 */
	public final static List<VariableTree> getAttributes(ClassTree node) {
		return node.getMembers().stream()
				.filter(member -> member.getKind() == Tree.Kind.VARIABLE)
				.map(attr -> (VariableTree)attr)
				.collect(Collectors.toList());
	}

	
	/**
	 * Returns the attribute  of the class tree node with the specified name
	 *  
	 * @param node   the class tree node
	 * @param name   the attributes name
	 * 
	 * @return the attribute of the class tree node
	 */
	public final static VariableTree getAttribute(ClassTree node, String name) {
		return node.getMembers().stream()
				.filter(member -> member.getKind() == Tree.Kind.VARIABLE)
				.map(attr -> (VariableTree)attr)
				.filter(v -> v.getName().toString().equals(name))
				.findFirst().orElse(null);
	}

	
	/**
	 * Returns the list of constructors of the class tree node.
	 *  
	 * @param node   the class tree node
	 * 
	 * @return the list of methods of the class tree node
	 */
	public final static List<MethodTree> getConstructors(ClassTree node) {
		return node.getMembers().stream()
				.filter(member -> (member.getKind() == Tree.Kind.METHOD) && ("<init>".equals("" + ((MethodTree)member).getName())))
				.map(attr -> (MethodTree)attr)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Returns the list of methods - excluding constructors - of the class 
	 * tree node.
	 *  
	 * @param node   the class tree node
	 * 
	 * @return the list of methods of the class tree node
	 */
	public final static List<MethodTree> getMethods(ClassTree node) {
		return node.getMembers().stream()
				.filter(member -> (member.getKind() == Tree.Kind.METHOD) && (!"<init>".equals("" + ((MethodTree)member).getName())))
				.map(attr -> (MethodTree)attr)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Returns the list of all methods - including constructors - of the class 
	 * tree node.
	 *  
	 * @param node   the class tree node
	 * 
	 * @return the list of methods of the class tree node
	 */
	public final static List<MethodTree> getAllMethods(ClassTree node) {
		return node.getMembers().stream()
				.filter(member -> member.getKind() == Tree.Kind.METHOD)
				.map(attr -> (MethodTree)attr)
				.collect(Collectors.toList());
	}

	
	/**
	 * Returns the methods of the class tree node with the specified name.
	 *  
	 * @param node   the class tree node
	 * @param name   the method name
	 * 
	 * @return the list of methods of the class tree node with the specified name
	 */
	public final static List<MethodTree> getMethods(ClassTree node, String name) {
		return node.getMembers().stream()
				.filter(member -> member.getKind() == Tree.Kind.METHOD)
				.map(attr -> (MethodTree)attr)
				.filter(m -> m.getName().toString().equals(name))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Checks whether the identifier is a class member if not access with "this.".
	 * 
	 * @param node   the identifier node to be checked
	 * 
	 * @return true if the identifier is a class member in this scope and false otherwise
	 */
	public final boolean isClassMember(IdentifierTree node) {
		TreePath path = getTreePath(node);
		TranspilerUnit tu = getTranspilerUnit(path);
		Set<Tree> scopesLocalMethods = tu.allLocalMethods.get("" + node.getName());
		Set<Tree> scopesLocalVariables = tu.allLocalVariables.get("" + node.getName());
		for (TreePath current = path; current.getParentPath() != null; current = current.getParentPath()) {
			Tree currentNode = current.getLeaf();
			if (((scopesLocalVariables != null) && (scopesLocalVariables.contains(currentNode))) || 
				((scopesLocalMethods != null) && (scopesLocalMethods.contains(currentNode))))
				return (currentNode.getKind() == Tree.Kind.CLASS) || (currentNode.getKind() == Tree.Kind.ENUM);
		}
		if (tu.allLocalMethodElements.containsKey(node.getName().toString()))
			return true;
		return false;
	}

	
	/**
	 * Checks whether the identifier is a static class member if not accessed
	 * by a member select with its class name.
	 * 
	 * @param node   the identifier node to be checked
	 * 
	 * @return true if the identifier is a static class member in this scope and false otherwise
	 */
	public final boolean isStaticClassMember(IdentifierTree node) {
		TreePath path = getTreePath(node);
		TranspilerUnit tu = getTranspilerUnit(path);
		Set<Tree> scopesLocalMethods = tu.allLocalMethods.get(node.getName().toString());
		Set<Tree> scopesLocalVariables = tu.allLocalVariables.get(node.getName().toString());
		for (TreePath current = path; current.getParentPath() != null; current = current.getParentPath()) {
			if (current.getLeaf() instanceof ClassTree ct) {
				if ((scopesLocalVariables != null) && (scopesLocalVariables.contains(ct))) {
					VariableTree attr = getAttribute(ct, node.getName().toString());
					return (attr != null) && attr.getModifiers().getFlags().contains(Modifier.STATIC);
				}
				if ((scopesLocalMethods != null) && (scopesLocalMethods.contains(ct))) {
					List<MethodTree> methods = getMethods(ct, node.getName().toString());
					return (methods.size() > 0) && (methods.get(0).getModifiers().getFlags().contains(Modifier.STATIC));
				}
			}
		}
		return false;
	}
	

	/**
	 * Returns the variable declaration for the specified identifier or null if a
	 * variable declaration is not found.
	 * 
	 * @param node   the identifiert 
	 * 
	 * @return the variable declaration or null
	 */
	public final VariableTree getDeclaration(IdentifierTree node) {
		TreePath path = getTreePath(node);
		TranspilerUnit tu = getTranspilerUnit(path);
		Set<Tree> scopesLocalVariables = tu.allLocalVariables.get("" + node.getName());
		for (TreePath current = path; current.getParentPath() != null; current = current.getParentPath()) {
			Tree currentNode = current.getLeaf();
			if (((scopesLocalVariables != null) && (scopesLocalVariables.contains(currentNode))))
				return tu.allLocalVariablesByScope.get(currentNode).get("" + node.getName());
		}
		return null;
	}
	
	
	/**
	 * Returns the class or enum that is the ancestor of this tree node. This may
	 * be an inner class. 
	 * 
	 * @param node   the tree node 
	 * 
	 * @return the parent class tree node
	 */
	public final ClassTree getClass(Tree node) {
		TreePath path = getTreePath(node);
		for (TreePath current = path; current.getParentPath() != null; current = current.getParentPath()) {
			Tree currentNode = current.getLeaf();
			if (currentNode instanceof ClassTree)
				return (ClassTree)currentNode;
		}
		return null;
	}
	
	
	/**
	 * Checks whether the annotation list contains a Not Null annotation from the 
	 * jakartax.validation package.
	 * 
	 * @param annotations   the list of annotation nodes
	 * @param tu            the transpiler unit
	 * 
	 * @return true if the list contains a not null annotation 
	 */
	public static boolean hasNotNullAnnotation(List<? extends AnnotationTree> annotations, TranspilerUnit tu) {
		if (annotations != null) {
			for (AnnotationTree annotation : annotations) {
				if ("NotNull".equals(annotation.getAnnotationType().toString())) {
					String packageName = tu.allAnnotations.get(annotation.getAnnotationType());
					if ("jakarta.validation.constraints".equals(packageName))
						return true;
				}
			}
		}
		return false;		
	}
	
	
	/**
	 * Checks whether the variable has a Not Null annotation from the 
	 * jakartax.validation package assigned.
	 * 
	 * @param node   the variable tree node
	 * 
	 * @return true if is has a not null annotation assigned
	 */
	public boolean hasNotNullAnnotation(VariableTree node) {
		return hasNotNullAnnotation(node.getModifiers().getAnnotations(), getTranspilerUnit(node));
	}
	
	
	/**
	 * Checks whether the method has a Not Null annotation from the 
	 * jakartax.validation package assigned for its return type.
	 * 
	 * @param node   the method tree node
	 * 
	 * @return true if is has a not null annotation assigned
	 */
	public boolean hasNotNullAnnotation(MethodTree node) {
		return hasNotNullAnnotation(node.getModifiers().getAnnotations(), getTranspilerUnit(node));
	}
	
	
	/**
	 * Checks whether the annotated type has a Not Null annotation from the 
	 * jakartax.validation package assigned.
	 * 
	 * @param node   the annotated type tree node
	 * 
	 * @return true if is has a not null annotation assigned
	 */
	public boolean hasNotNullAnnotation(AnnotatedTypeTree node) {
		return hasNotNullAnnotation(node.getAnnotations(), getTranspilerUnit(node));
	}
	

	/**
	 * Checks whether the annotation list contains a TranspilerDTO annotation.
	 * 
	 * @param annotations   the list of annotation nodes
	 * @param tu            the transpiler unit
	 * 
	 * @return true if the list contains a TranspilerDTO annotation 
	 */
	public static boolean hasTranspilerDTOAnnotation(List<? extends AnnotationTree> annotations, TranspilerUnit tu) {
		if (annotations != null) {
			for (AnnotationTree annotation : annotations) {
				if ("TranspilerDTO".equals(annotation.getAnnotationType().toString())) {
					String packageName = tu.allAnnotations.get(annotation.getAnnotationType());
					if ("de.nrw.schule.svws.core.transpiler".equals(packageName))
						return true;
				}
			}
		}
		return false;		
	}
	
	
	/**
	 * Checks whether the class tree node has a TranspilerDTO annotation.
	 * 
	 * @param node   the class tree node
	 * 
	 * @return true if the class has a TranspilerDTO annotation
	 */
	public boolean hasTranspilerDTOAnnotation(ClassTree node) {
		return hasTranspilerDTOAnnotation(node.getModifiers().getAnnotations(), getTranspilerUnit(node));
	}
	
	
	/**
	 * Returns the annotation of the annotation list which has specified type.
	 * 
	 * @param annotationType   the name of the annotation type (e.g. )
	 * @param tree             the class or method where to check for the annotation
	 * 
	 * @return the annotation if an annotation of the specified type exists 
	 */
	public AnnotationTree getAnnotation(String annotationType, Tree tree) {
		ModifiersTree mods = null;
		if (tree instanceof ClassTree classTree) {
			mods = classTree.getModifiers();
		} else if (tree instanceof MethodTree methodTree) {
			mods = methodTree.getModifiers();
		} else if (tree instanceof VariableTree varTree) {
			mods = varTree.getModifiers();
		}
		if (mods == null)
			return null;
		TranspilerUnit tu = getTranspilerUnit(tree);
		if (tu == null)
			return null;
		List<? extends AnnotationTree> annotations = mods.getAnnotations();
		if (annotations != null) {
			int pos = annotationType.lastIndexOf('.');
			String annotationClassName = annotationType.substring(pos + 1);
			String annotationPackageName = annotationType.substring(0, pos);		
			for (AnnotationTree annotation : annotations) {
				Tree type = annotation.getAnnotationType();
				String packageName = tu.annotations.get(type.toString());
				if ((annotationClassName.equals(type.toString())) && (annotationPackageName.equals(packageName)))
					return annotation;
			}
		}
		return null;		
	}
	

	/**
	 * Returns whether the specified annotation has the specified type
	 *  
	 * @param annotationType   the annotation types fully qualified name
	 * @param annotation       the annotation to be checked
	 * 
	 * @return true if the annotation has the specified type
	 */
	public boolean isAnnotationType(String annotationType, AnnotationTree annotation) {
		TranspilerUnit tu = getTranspilerUnit(annotation);
		if (tu == null)
			return false;
		int pos = annotationType.lastIndexOf('.');
		String annotationClassName = annotationType.substring(pos + 1);
		String annotationPackageName = annotationType.substring(0, pos);		
		Tree type = annotation.getAnnotationType();
		String packageName = tu.annotations.get(type.toString());
		return ((annotationClassName.equals(type.toString())) && (annotationPackageName.equals(packageName)));
	}
	

	/**
	 * Returns all annotation of the annotation list which has specified type.
	 * 
	 * @param annotationType   the name of the annotation type (e.g. )
	 * @param tree             the class or method where to check for the annotation
	 * 
	 * @return the list of all annotations of the specified type 
	 */
	public Vector<AnnotationTree> getAnnotationList(String annotationType, Tree tree) {
		Vector<AnnotationTree> result = new Vector<>();
		ModifiersTree mods = null;
		if (tree instanceof ClassTree classTree) {
			mods = classTree.getModifiers();
		} else if (tree instanceof MethodTree methodTree) {
			mods = methodTree.getModifiers();
		}
		if (mods == null)
			return result;
		TranspilerUnit tu = getTranspilerUnit(tree);
		if (tu == null)
			return result;
		List<? extends AnnotationTree> annotations = mods.getAnnotations();
		if (annotations != null) {
			int pos = annotationType.lastIndexOf('.');
			String annotationClassName = annotationType.substring(pos + 1);
			String annotationPackageName = annotationType.substring(0, pos);		
			for (AnnotationTree annotation : annotations) {
				Tree type = annotation.getAnnotationType();
				String packageName = tu.annotations.get(type.toString());
				if ((annotationClassName.equals(type.toString())) && (annotationPackageName.equals(packageName)))
					result.add(annotation);
			}
		}
		return result;		
	}


	/**
	 * Returns for the specified {@link AnnotationTree} a mapping of all variable names to 
	 * their assigned {@link ExpressionTree}.
	 *  
	 * @param annotation   the annotation tree
	 * 
	 * @return the mapping
	 */
	public Map<String, ExpressionTree> getArguments(AnnotationTree annotation) {
		HashMap<String, ExpressionTree> result = new HashMap<>();
		if (annotation == null)
			return result;
		List<? extends ExpressionTree> args = annotation.getArguments();
		for (ExpressionTree expr : args) {
			if (expr instanceof AssignmentTree arg) {
				result.put(arg.getVariable().toString(), arg.getExpression());
				continue;
			}
			throw new TranspilerException("Transpiler Error: Unhandled annotation argument type.");
		}
		return result;
	}
	
	
	/**
	 * Checks whether the java full qualified name is the name of a functional interface
	 * or not.
	 *  
	 * @param name   the full qualified name
	 *  
	 * @return true, if the name is the name of a functional interface, or false otherwise 
	 */
	boolean isFunctionalInterface(String name) {
		TypeElement elem = this.getTypeElement(name);
		return (elem.getAnnotation(FunctionalInterface.class) != null);
	}
	
	
	/**
	 * Checks whether the java full qualified name is the name of a functional interface
	 * or not and returns the name of the abstract method name for the specified functional
	 * interface.
	 *  
	 * @param name   the full qualified name of the interface
	 *  
	 * @return the method name or null if name is not the name of a functional interface  
	 */
	public String getFunctionInterfaceMethodName(String name) {
		TypeElement typeElem = this.getTypeElement(name);
		if (typeElem.getAnnotation(FunctionalInterface.class) == null)
			return null;
		List<? extends Element> elems = typeElem.getEnclosedElements();
		for (Element elem : elems) {
			Set<Modifier> mods = elem.getModifiers();
			if (!mods.contains(Modifier.ABSTRACT))
				continue;
			if (elem instanceof ExecutableElement ee) {
				if (ee.isDefault())
					continue;
				return ee.getSimpleName().toString();
			}
		}
		return null;
	}
	
	
	/**
	 * Checks whether the specified identifier tree node is part of an annotation
	 * tree but not the type of an annotation.
	 * 
	 * @param node   the identifier tree node
	 * 
	 * @return true, if the specified identifier tree node is part of an annotation
	 *     tree, and false otherwise 
	 */
	public boolean isAnnotationArgument(IdentifierTree node) {
		TreePath path = getTreePath(node).getParentPath();
		Tree parent = path.getLeaf();
		if (parent instanceof AnnotationTree at)
			return (at.getAnnotationType() != node);
		for (; path != null; path = path.getParentPath())
			if (path.getLeaf() instanceof AnnotationTree)
				return true;
		return false;
	}
	
	
	/**
	 * Gets the type object associated with specified expression tree node.
	 *  
	 * @param node   the expression tree node
	 * 
	 * @return the type object
	 */
	public ExpressionType getExpressionType(ExpressionTree node) {
		return getTranspilerUnit(node).allExpressionTypes.get(node);
	}
	
	
	/**
	 * Returns the set of modifier associated with the tree node.
	 * 
	 * @param node   the tree node 
	 * 
	 * @return the node of modifiers
	 */
	private Set<Modifier> getModifier(Tree node) {
		Set<Modifier> modifier = allModifier.get(node); 
		return modifier == null ? Collections.emptySet() : modifier;
	}
	
	
	/**
	 * Returns the access modifier associated with the tree node as a string. 
	 * If there is none an empty string is returned.
	 * 
	 * @param node   the tree node 
	 *  
	 * @return the access modifier string or an empty string.
	 */
	public final String getAccessModifier(Tree node) {
		Set<Modifier> modifier = getModifier(node); 
		if (modifier.contains(Modifier.PRIVATE))
			return Modifier.PRIVATE.toString();
		if (modifier.contains(Modifier.PROTECTED))
			return Modifier.PROTECTED.toString();
		if (modifier.contains(Modifier.PUBLIC))
			return Modifier.PUBLIC.toString();
		return "";
	}
	
	
	/**
	 * Retrieves the method that belongs to the specified return tree.  
	 * 
	 * @param node    the return tree node
	 * 
	 * @return the tree of the method or the lambda. 
	 */
	public final Tree getMethod(ReturnTree node) {
		Tree parent = getParent(node);
		while ((parent != null) && (!(parent instanceof CompilationUnitTree))) {
			if ((parent instanceof MethodTree) || (parent instanceof LambdaExpressionTree))
				return parent;
			parent = getParent(parent);
		}
		throw new TranspilerException("Transpiler Error: Cannot determine the surrounding method for the return node '" + node.toString() + "'");
	}
	
	
	/**
	 * Returns whether the tree node has an abstract modifier or not.
	 * 
	 * @param node   the tree node
	 * 
	 * @return true if the tree node has an abstract modifier and false otherwise
	 */
	public boolean hasAbstractModifier(Tree node) {
		return getModifier(node).contains(Modifier.ABSTRACT);
	}
	
	
	/**
	 * Returns whether a final modifier is associated with the tree node.
	 * 
	 * @param node   the tree node
	 * 
	 * @return true if the tree node has a final modifier and false otherwise
	 */
	public final boolean hasFinalModifier(Tree node) {
		return getModifier(node).contains(Modifier.FINAL);
	}
	
	
	/**
	 * Returns whether a static modifier is associated with the tree node.
	 * 
	 * @param node   the tree node
	 * 
	 * @return true if the tree node has a static modifier and false otherwise
	 */
	public final boolean hasStaticModifier(Tree node) {
		return getModifier(node).contains(Modifier.STATIC);
	}
	
	
	/**
	 * Retrieves the java compiler element for the tree node or null if none exists.  
	 * 
	 * @param node   the tree node
	 * 
	 * @return the java compiler element
	 */
	Element getElement(Tree node) {
		return trees.getElement(getTreePath(node));
	}
	
	
	/**
	 * Returns the type element for the specified canonical name 
	 * 
	 * @param name   the canonical name 
	 * 
	 * @return the type element
	 */
	TypeElement getTypeElement(String name) {
		return elementUtils.getTypeElement(name);
	}
	

	/**
	 * Returns the type of the specified class attribute member.
	 * 
	 * @param className    the full qualified name of the class, i.e. the canonical name
	 * @param memberName   the name of the attribute member
	 * 
	 * @return the type of the class attribute member
	 */
	ExpressionType getAttributeType(String className, String memberName) {
		TypeElement classElement = getTypeElement(className);
		for (Element e : classElement.getEnclosedElements()) {
			if (e instanceof VariableElement ve) {
				if (memberName.equals(ve.getSimpleName().toString()))
					return ExpressionType.getExpressionType(this, ve.asType());
			}
		}
		return null;
	}
	
	
	/**
	 * Returns the type of the specified nested class or interface member.
	 * 
	 * @param className    the full qualified name of the class, i.e. the canonical name
	 * @param memberName   the name of the nestedt class or interface member
	 * 
	 * @return the type of the nested class or interface member
	 */
	ExpressionType getNestedType(String className, String memberName) {
		TypeElement classElement = getTypeElement(className);
		for (Element e : classElement.getEnclosedElements()) {
			if (e instanceof TypeElement ve) {
				if (memberName.equals(ve.getSimpleName().toString()))
					return ExpressionType.getExpressionType(this, ve.asType());
			}
		}
		return null;
	}
	
	
	/**
	 * Checks whether the specified class type has the specified super type,
	 * either a class type or an interface type
	 * 
	 * @param classType   the class type
	 * @param superType   the super type
	 * 
	 * @return -1 on error or a positive value signaling the depth in the super class / interface tree
	 */
	public int checkForSuperclass(ExpressionType classType, ExpressionType superType) {
		if ((!(classType instanceof ExpressionClassType)) || (!(superType instanceof ExpressionClassType)))
			return -1;
		String superTypeName = ((ExpressionClassType)superType).getFullQualifiedName();
		String classTypeWithoutTypeArgs = ((ExpressionClassType)classType).getFullQualifiedName();
		TypeElement te = elementUtils.getTypeElement(classTypeWithoutTypeArgs);
		if (te == null)
			return -1;
		if (te.toString().equals(superTypeName))
			return 0;
		// check all interfaces of the class
		LinkedCollection<TypeMirror> interfaces = new LinkedCollection<>();
		interfaces.addAll(te.getInterfaces());
		while (interfaces.size() > 0) {
			TypeMirror interfaceType = interfaces.removeFirst();
			Element interfaceElement = typeUtils.asElement(interfaceType);
			if (interfaceElement instanceof TypeElement ite) {
				if (ite.toString().equals(superTypeName))
					return 1;
				interfaces.addAll(ite.getInterfaces());
			}
		}
//		for (TypeMirror interfaceType : te.getInterfaces()) {
//			Element interfaceElement = typeUtils.asElement(interfaceType);
//			if (interfaceElement instanceof TypeElement ite) {
//				if (ite.toString().equals(superTypeName))
//					return 1;								
//			} else continue;
//		}
		// check all super classes
		TypeMirror superClass = te.getSuperclass();
		int i = 2;
		while (superClass.getKind() != TypeKind.NONE) {
			Element superElement = typeUtils.asElement(superClass);
			if (superElement instanceof TypeElement ste) {
				if (ste.toString().equals(superTypeName))
					return i;				
				for (TypeMirror interfaceType : ste.getInterfaces()) {
					Element interfaceElement = typeUtils.asElement(interfaceType);
					if (interfaceElement instanceof TypeElement ite) {
						if (ite.toString().equals(superTypeName))
							return i+1;
					} else continue;
				}
				superClass = ste.getSuperclass();
				i += 2;
			} else break;
		}
		if ("java.lang.Object".equals(superTypeName))
			return i;
		return -1;
	}
	
	
	
	/**
	 * Returns the return type of the specified class method member.
	 * 
	 * @param unit             the transpiler unit which called this method
	 * @param mst              the member select tree which is analyzed here
	 * @param clazz            the class
	 * @param memberName       the name of the method member
	 * @param parameterTypes   the parameter types for the method invocation tree to distinguish methods 
	 * 
	 * @return the type of the class method member
	 */
	ExpressionType getMethodReturnType(TranspilerUnit unit, MemberSelectTree mst, 
			ExpressionClassType clazz, String memberName, List<ExpressionType> parameterTypes) {
		TypeElement classElement = getTypeElement(clazz.getFullQualifiedName());
		if (classElement == null)
			throw new TranspilerException("Transpiler Error: Cannot retrieve class element.");

		// TODO implement this mapping in ExpressionClassType 
		// create a map for the type argument of the class
		List<? extends TypeParameterElement> classTypeParams = classElement.getTypeParameters();
		if (clazz.getTypeArgumentCount() != classTypeParams.size())
			throw new TranspilerException("Transpiler Error: Number of class type argument do not match");
		HashMap<String, ExpressionType> mapClassTypeArgs = new HashMap<>();
		for (int i = 0; i < classTypeParams.size(); i++)
			mapClassTypeArgs.put(classTypeParams.get(i).toString(), clazz.getTypeArguments().get(i));
		
		// try to find a method
		ExpressionType found = null;
		int found_rated = Integer.MAX_VALUE;
		nextMethod:
		for (Element e : elementUtils.getAllMembers(classElement)) {
			if (e instanceof ExecutableElement ee) {
				if (!memberName.equals(ee.getSimpleName().toString()))
					continue;
				// check method parameter types
				List<? extends VariableElement> elemParams = ee.getParameters();
				if (!((elemParams.size() == parameterTypes.size()) || 
						((elemParams.size() > 0) && (elemParams.size() - 1 <= parameterTypes.size()) && ee.isVarArgs())))
					continue;
				int rated = 0;
				HashMap<String, ExpressionType> mapMethodTypeArgs = new HashMap<>(mapClassTypeArgs);
				for (TypeParameterElement typeParam : ee.getTypeParameters())
					mapMethodTypeArgs.put(typeParam.toString(), new ExpressionTypeNone(TypeKind.NONE));
				for (int i = 0 ; i < parameterTypes.size(); i++) {
					ExpressionType paramType = parameterTypes.get(i);
					
					// Determine vararg Type of the executable element
					TypeMirror tmpType; 
					if ((i >= elemParams.size() - 1) && ee.isVarArgs()) {
						tmpType = elemParams.get(elemParams.size() - 1).asType();
						if (tmpType.getKind() != TypeKind.ARRAY)
							continue;
						// special check whether to skip determining the array component type if an array is passed as parameter type (also check whether the vararg is a vararg of arrays)   
						boolean doGetComponent = true;
						ExpressionType tmpElemType = ExpressionType.getExpressionType(this, tmpType);
						if ((paramType instanceof ExpressionArrayType eat) && (tmpElemType instanceof ExpressionArrayType eat2) && (eat.getDimensions() == eat2.getDimensions()))
							doGetComponent = false;
						if (doGetComponent)
							tmpType = ((ArrayType)tmpType).getComponentType();
					} else { // assume that no varargs are used
						tmpType = elemParams.get(i).asType();
					}
					ExpressionType elemType = ExpressionType.getExpressionType(this, tmpType);
					
					// substitute the type if a type variable is directly used
					if (elemType instanceof ExpressionTypeVar tv) {
						// substitute the type if a class type variable is used
						if (mapClassTypeArgs.get(tv.getName()) != null)
							elemType = mapClassTypeArgs.get(tv.getName());
						// substitute the type if a method type variable is used
						ExpressionType exprType = mapMethodTypeArgs.get(tv.getName());
						if (exprType != null) {
							if (exprType instanceof ExpressionTypeNone) {
								elemType = paramType;
								mapMethodTypeArgs.put(tv.getName(), elemType);
							} else {
								elemType = exprType;
							}
						}
					}
					
					// substitute the type if a type variable is used inside an array type
					if ((elemType instanceof ExpressionArrayType et) && (et.getType() instanceof ExpressionTypeVar tv) && (paramType instanceof ExpressionArrayType pt)) {
						// substitute the type if a class type variable is used
						if (mapClassTypeArgs.get(tv.getName()) != null)
							elemType = new ExpressionArrayType(mapClassTypeArgs.get(tv.getName()), et.getDimensions());
						// substitute the type if a method type variable is used
						ExpressionType exprType = mapMethodTypeArgs.get(tv.getName());
						if (exprType != null) {
							if (exprType instanceof ExpressionTypeNone) {
								elemType = paramType;
								mapMethodTypeArgs.put(tv.getName(), (ExpressionType)pt.getType());
							} else {
								elemType = new ExpressionArrayType(exprType, et.getDimensions());
							}
						}
					}
					
					// TODO substitute the type if a type variable is used inside a class expression type - check recursively
					
					// Check whether it is assignable
					int a = elemType.isAssignable(this, paramType);
					if (a >= 0) {
						rated += a;
						continue;
					}

					// no fitting parameter type found - skip this method 
					continue nextMethod;
				}
				// determine the best method implementation and its return type by their rating
				if ((found == null) || (rated < found_rated)) {
					TypeMirror returnType = ee.getReturnType();
					if (returnType.getKind() == TypeKind.TYPEVAR) {
						found = mapClassTypeArgs.get(returnType.toString());
						if (found == null) {
							found = mapMethodTypeArgs.get(returnType.toString());
							// TODO improve the above analysis for mapMethodTypeArgs
						}
					} else {
						found = ExpressionType.getExpressionType(this, returnType);
					}
					if (found instanceof ExpressionClassType foundClassType) {
						if (!foundClassType.resolveTypeVariables(mapMethodTypeArgs))
							throw new TranspilerException("Transpiler Error: Cannot resolve all type vars for method return type for " + clazz.getFullQualifiedName() + "." + memberName);
						// TODO improvement: replace type variables recursively - see comment in ExpressionClassType
					} else if (found instanceof ExpressionArrayType foundArrayType)  {
						if (foundArrayType.getType() instanceof ExpressionTypeVar tv) {
							ExpressionType tmp = mapMethodTypeArgs.get(tv.getName());
							if (tmp == null)
								throw new TranspilerException("Transpiler Error: Cannot resolve all type vars for method return type for " + clazz.getFullQualifiedName() + "." + memberName);
							found = new ExpressionArrayType(tmp, foundArrayType.getDimensions());
						}
					}
					found_rated = rated;
					unit.allInvokedMethods.put(mst, ee);
				}
			}
		}
		return found;
	}
	
	
	/**
	 * Retrieves the comment for the specified tree node or null if none exists. 
	 * 
	 * @param node   the tree node
	 * 
	 * @return the comment or null
	 */
	public String getComment(Tree node) {
		return trees.getDocComment(getTreePath(node));
	}
	
	
	/**
	 * Retrieves the compilation unit for the specified tree node.
	 * 
	 * @param node   the tree node 
	 * 
	 * @return the compilation units tree node
	 */
	public CompilationUnitTree getCompilationUnit(Tree node) {
		return getTreePath(node).getCompilationUnit();
	}
	
	
	
	/* Overrides {@link AbstractProcessor#init(ProcessingEnvironment)} for reading
	 * all trees of the Tree-API of the Java Compiler. */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
        trees = Trees.instance(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }
	

	
	/* Overrides {@link AbstractProcessor#process(Set, RoundEnvironment)} for processing 
	 * the elements given by the parameter roundEnv. */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (!roundEnv.processingOver()) {
			// first register all transpiler units
			for (final Element element : roundEnv.getRootElements()) {
				System.out.println("  -> Preparing: " + element.toString());
				TreePath path = trees.getPath(element);
				TranspilerUnit tu = new TranspilerUnit(this, path.getCompilationUnit(), (ClassTree)path.getLeaf(), (TypeElement) element);
				mapUnits.put(path.getCompilationUnit(), tu);
				String packageName = tu.getPackageName();
				HashMap<String, TranspilerUnit> listUnits = mapUnitsByPackage.get(packageName);
				if (listUnits == null) {
					listUnits = new HashMap<>();
					mapUnitsByPackage.put(packageName, listUnits);
				}
				listUnits.put(tu.getClassName(), tu);
			}
			
			// scan the java compiler Tree-API and register all tree nodes with their path, identifier, variable and method names
			for (final Element element : roundEnv.getRootElements()) {
				System.out.println("  -> Scanning: " + element.toString());
				TreePath path = trees.getPath(element);
				scanner.scan(path, this);
			}

			// prepare the transpiler units for transpilation
			for (TranspilerUnit unit : mapUnits.values().stream().sorted((a,b) -> (a.getPackageName() + "." + a.getClassName()).compareTo(b.getPackageName() + "." + b.getClassName())).collect(Collectors.toList())) {
				System.out.println("  -> Preparing: " + unit.getPackageName() + "." + unit.getClassName());
				// determine all local attribute and method identifiers of super classes and instantiated interfaces
				unit.determineInheritedMembers(unit.getElement());
				// filter all local identifiers and get all classes that should be imported somehow
				unit.determineImports();
				// determine all expression types in the transpiler unit
				unit.determineExpressionTypes();
			}
			
			// perform the code transpilation
			for (TranspilerLanguagePlugin plugin : languagePlugins) {
				plugin.transpile();
			}
		}
		return true;
	}
	
}
