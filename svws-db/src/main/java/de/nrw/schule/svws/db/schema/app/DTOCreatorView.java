package de.nrw.schule.svws.db.schema.app;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.nrw.schule.svws.db.converter.DBAttributeConverter;
import de.nrw.schule.svws.db.schema.Schema;
import de.nrw.schule.svws.db.schema.View;
import de.nrw.schule.svws.db.schema.ViewSpalte;

/**
 * Diese Klasse stellt Methoden zum Erstellen des Java Quellcodes
 * für eine DTO-Klasse zum Zugriff auf eine View der SVWS-Datenbank
 * zur Verfügung.  
 */
public class DTOCreatorView {

	/** Die View für die der Java-Code erzeugt werden soll */
	public final View view;


	/**
	 * Erzeugt ein neues Objekt der Klasse DTOCreatorTable.
	 * 
	 * @param view    die View, für die der Java-Code erstellt werden soll.
	 */
	public DTOCreatorView(View view) {
		this.view = view;
	}


	/**
	 * Gibt den Package-Namen für diese View zurück.
	 *  
	 * @param rev   die Revision des Datenbankschemas, für welche der DTO für die View erzeugt wird. 
	 * 
	 * @return der Package-Name
	 */
	public String getPackageName(long rev) {
		if (rev == 0)
			throw new IllegalArgumentException("Java-DTOs für Views brauchen nicht für die Migration erstellt werden.");
		return Schema.javaPackage + "." 
			+ Schema.javaDTOPackage 
			+ ((rev < 0) ? ".current." : ".dev.") 
			+ view.packageName;
	}
	
	
    /**
     * Liefert den Namen der Java-Klasse, wie er in der angegebenn Revision genutzt werden soll.
     * 
     * @param rev   die Revision
     * 
     * @return der Name der Java-Klasse
     */
    @JsonIgnore
    public String getJavaKlasse(final long rev) {
    	if (rev == 0)
			throw new IllegalArgumentException("Java-DTOs für Views brauchen nicht für die Migration erstellt werden.");
		return (rev > 0) ? "Dev" + view.dtoName : view.dtoName;
	}	
	
	
	/**
	 * Generiert die Equals und HashCode-Methoden für die DTO-Klasse.
	 * 
	 * @param classname   der Name der DTO-Klasse
	 * @param pkspalten   die Primärschlüsselattribute für die DTO-Klassen
	 * 
	 * @return der Java-Code für die beiden Methoden 
	 */
	private static String getCode4EqualsAndHashcode(String classname, Collection<ViewSpalte> pkspalten) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t@Override" + System.lineSeparator());		
		sb.append("\tpublic boolean equals(Object obj) {" + System.lineSeparator());
		sb.append("\t\tif (this == obj)" + System.lineSeparator());
		sb.append("\t\t\treturn true;" + System.lineSeparator());
		sb.append("\t\tif (obj == null)" + System.lineSeparator());
		sb.append("\t\t\treturn false;" + System.lineSeparator());
		sb.append("\t\tif (getClass() != obj.getClass())" + System.lineSeparator());
		sb.append("\t\t\treturn false;" + System.lineSeparator());
		sb.append("\t\t" + classname + " other = (" + classname + ") obj;" + System.lineSeparator());
		sb.append(pkspalten.stream()
				.map(col -> col.name)
				.filter(name -> name != null)
				.map(colname -> 
				        "\t\tif (" + colname + " == null) {" + System.lineSeparator() +
						"\t\t\tif (other." + colname + " != null)" + System.lineSeparator() + 
						"\t\t\t\treturn false;" + System.lineSeparator() + 
						"\t\t} else if (!" + colname + ".equals(other." + colname + "))" + System.lineSeparator() + 
						"\t\t\treturn false;" + System.lineSeparator())
				.filter(code -> code != null)
				.collect(Collectors.joining(System.lineSeparator())));
		sb.append("\t\treturn true;" + System.lineSeparator());
		sb.append("\t}" + System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append("\t@Override" + System.lineSeparator());		
		sb.append("\tpublic int hashCode() {" + System.lineSeparator());
		sb.append("\t\tfinal int prime = 31;" + System.lineSeparator());
		sb.append("\t\tint result = 1;" + System.lineSeparator());
		sb.append(pkspalten.stream()
				.map(col -> col.name)
				.filter(name -> name != null)
				.map(colname -> "\t\tresult = prime * result + ((" + colname + " == null) ? 0 : " + colname + ".hashCode());" + System.lineSeparator())
				.filter(code -> code != null)
				.collect(Collectors.joining(System.lineSeparator())));		
		sb.append("\t\treturn result;" + System.lineSeparator());
		sb.append("\t}" + System.lineSeparator());		
		return sb.toString();
	}

	
	
	/**
	 * Ermittelt die Attribut-Konverter {@link DBAttributeConverter}, die für diese View definiert wurden.
	 * 
	 * @param rev   die DB-Revision, für welche die Attribut-Konverter bestimmt werden sollen
	 * 
	 * @return eine Liste mit den Attribut-Konvertern.
	 */
	private List<DBAttributeConverter<?,?>> getAttributeConverter(final long rev) {
		return view.spalten.stream()
				.filter(spalte -> spalte.converter != null)
				.map(spalte -> DBAttributeConverter.getByClass(spalte.converter))
				.filter(ac -> ac != null)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Generiert den Code für den Import der übergebenen Attribut-Konverter {@link DBAttributeConverter}.
	 * 
	 * @param acs   eine Liste von Attribut-Konvertern
	 * 
	 * @return der Java-Code für den Import der Attribut-Konverter
	 */
	private static String getCodeImportConverter(List<DBAttributeConverter<?,?>> acs) {
		if (acs.size() == 0)
			return "";
		String result = "import " 
			+ acs.stream().map(ac -> ac.getClass().getName()).filter(cn -> cn != null).sorted().distinct()
				.collect(Collectors.joining(";" + System.lineSeparator() + "import "))
		    + ";" + System.lineSeparator()
            + System.lineSeparator();
		String resultTypeImports = acs.stream().map(ac -> ac.getResultType().getName())
				.filter(cntt -> cntt != null).filter(cntt -> !cntt.startsWith("java.lang")).sorted().distinct()
				.collect(Collectors.joining(";" + System.lineSeparator() + "import "));
		if (!"".equals(resultTypeImports))
			result += "import "
				+ resultTypeImports
				+ ";" + System.lineSeparator()
				+ System.lineSeparator();
		return result;
	}

	
	
	/**
	 * Generiert den Java Code für die "Named Queries", die als Annotation oberhalb der Klasse generiert werden.
	 * 
	 * @param rev   die Datenbank-Revision, für welche die "Named Queries" generiert werden
	 * 
	 * @return der Java Code
	 */
	private String getCode4NamedQueries(final long rev) {
		String className = getJavaKlasse(rev);
		// alle Views: Generiere Code für eine NamedQuery auf alle Datensätze der View
		String code 
			= "@NamedQuery(name=\"" + className + ".all\", query=\"SELECT e FROM " + className + " e\")"
			+ System.lineSeparator();
		// alle Views: Generiere Annotationen für NamedQueries für einzelne Attribute der View
		for (ViewSpalte spalte : view.spalten) {
			if (spalte.name.startsWith("-"))
				continue; // ignoriere Datenbank-Spalten, welche nicht als Java-Attribute umgesetzt werden sollen
			code += "@NamedQuery(name=\"" + className + "." + spalte.name.toLowerCase() + "\", query=\"SELECT e FROM " + className + " e WHERE "
				 + "e." + spalte.name + " = :value"
				 + "\")" + System.lineSeparator();
			code += "@NamedQuery(name=\"" + className + "." + spalte.name.toLowerCase() + ".multiple\", query=\"SELECT e FROM " + className + " e WHERE "
					 + "e." + spalte.name + " IN :value"
					 + "\")" + System.lineSeparator();
		}
		// nur für Views mit Primärschlüssel...
		if (view.pkSpalten.size() > 0) {
			// Generiere Code für eine parametrisierte NamedQuery mit den Spalten des Primärschlüssels als Parameter
			code += "@NamedQuery(name=\"" + className + ".primaryKeyQuery\", query=\"SELECT e FROM " + className + " e WHERE ";
			for (int i = 0; i < view.pkSpalten.size(); i++) {
				ViewSpalte col = view.pkSpalten.get(i);
				if (i > 0)
					code += " AND "; 
				code += "e." + col.name + " = ?" + (i+1);
			}
			code += "\")" + System.lineSeparator(); 
		}
		return code;
	}



	/**
	 * Generiert das Attribut für eine Java-Klasse, welches die angegebene Datenbank-Spalte 
	 * repräsentiert. <br>
	 * 
	 * @param spalte            die Spalte für die das Java-Attribut generiert wird
	 * @param rev               die DB Revision, für welche das Attribut erzeugt wird.
	 * @param withAnnotations   gibt an, ob auch Annotationen für die Spalte generiert werden sollen.
	 *  
	 * @return der Java-Code für das Attribut für die DB-Spalte
	 */
	private String getCode4Attributes(ViewSpalte spalte, final long rev, boolean withAnnotations) {
		StringBuilder sb = new StringBuilder();
		if (spalte.beschreibung != null) 
			sb.append("\t/** " + spalte.beschreibung + " */" + System.lineSeparator());
		if (withAnnotations) {
			if ((view.pkSpalten.size() <= 0) || view.pkSpalten.contains(spalte))
				sb.append("\t@Id" + System.lineSeparator());
			sb.append("\t@Column(name = \"" + spalte.name + "\")" + System.lineSeparator());
			sb.append("\t@JsonProperty" + System.lineSeparator());
		}
		if (spalte.converter != null) {
			if (withAnnotations) {
				var simpleConverterClassName = DBAttributeConverter.getByClass(spalte.converter).getClass().getSimpleName();
				sb.append("\t@Convert(converter=" + simpleConverterClassName + ".class)" + System.lineSeparator());
				sb.append("\t@JsonSerialize(using=" + simpleConverterClassName + "Serializer.class)" + System.lineSeparator());
				sb.append("\t@JsonDeserialize(using=" + simpleConverterClassName + "Deserializer.class)" + System.lineSeparator());				
			}
		}
		sb.append("\tpublic " + spalte.datentyp + " " + spalte.name + ";" + System.lineSeparator());
		return sb.toString();
	}
	

	
	/**
	 * Generiert für die angegebene Revision den Java-Code der dazugehörigen Java-DTO-Klasse.
	 * 
	 * @param rev    die Revision
	 * 
	 * @return der Java-Code für die DTO-Klasse in der angegebenen Revision.
	 */
	public String getCode(final long rev) {
		var acs = getAttributeConverter(rev);
		String className = getJavaKlasse(rev);

		StringBuilder sb = new StringBuilder();
		sb.append("package " + getPackageName(rev) + ";" + System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append("import de.nrw.schule.svws.db.DBEntityManager;" + System.lineSeparator());
		if (acs.size() != 0)
			sb.append(getCodeImportConverter(acs));
		sb.append(System.lineSeparator());
		
		sb.append("import jakarta.persistence.Cacheable;" + System.lineSeparator());
		sb.append("import jakarta.persistence.Column;" + System.lineSeparator());
		if (acs.size() != 0)
			sb.append("import jakarta.persistence.Convert;" + System.lineSeparator());
		sb.append("import jakarta.persistence.Entity;" + System.lineSeparator());
		sb.append("import jakarta.persistence.Id;" + System.lineSeparator());
		if (view.pkSpalten.size() != 1)
			sb.append("import jakarta.persistence.IdClass;" + System.lineSeparator());
		sb.append("import jakarta.persistence.NamedQuery;" + System.lineSeparator());
		sb.append("import jakarta.persistence.Table;" + System.lineSeparator());
		sb.append(System.lineSeparator());
		
		sb.append("import com.fasterxml.jackson.annotation.JsonProperty;" + System.lineSeparator());
		sb.append("import com.fasterxml.jackson.annotation.JsonPropertyOrder;" + System.lineSeparator());
		if (acs.size() != 0) {
			sb.append("import com.fasterxml.jackson.databind.annotation.JsonDeserialize;" + System.lineSeparator());
			sb.append("import com.fasterxml.jackson.databind.annotation.JsonSerialize;" + System.lineSeparator());
			String csvImportConverter = acs.stream()
					.map(ac -> ac.getClass().getName())
					.filter(cn -> cn != null).sorted().distinct()
					.map(cn -> cn.replace(".db.", ".csv.") + "Serializer;" + System.lineSeparator() + "import " + cn.replace(".db.", ".csv.") + "Deserializer")
					.collect(Collectors.joining(";" + System.lineSeparator() + "import "));
			sb.append("import " + csvImportConverter + ";" + System.lineSeparator());
			sb.append(System.lineSeparator());
		}

		sb.append("/**" + System.lineSeparator());
		sb.append(" * Diese Klasse dient als DTO für die Datenbank-View " + view.name + "." + System.lineSeparator());
		sb.append(" * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, " + System.lineSeparator());
		sb.append(" * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird." + System.lineSeparator());
		sb.append(" */" + System.lineSeparator());		
		sb.append("@Entity" + System.lineSeparator());
		if (view.pkSpalten.size() != 1) {
			sb.append("@IdClass(" + className + "PK.class)" + System.lineSeparator());
		}
		sb.append("@Cacheable(DBEntityManager.use_db_caching)" + System.lineSeparator());
		sb.append("@Table(name = \"" + view.name + "\")" + System.lineSeparator());
		sb.append(getCode4NamedQueries(rev));
		sb.append(view.spalten.stream()
				.map(spalte -> "\"" + spalte.name + "\"")
				.collect(Collectors.joining(",","@JsonPropertyOrder({","})" + System.lineSeparator())));		
		sb.append("public class " + className + " {" + System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append(view.spalten.stream()
				.map(spalte -> getCode4Attributes(spalte, rev, true))
				.filter(code -> code != null)
				.collect(Collectors.joining(System.lineSeparator())));
		sb.append(System.lineSeparator());
		sb.append("\t/**" + System.lineSeparator());
		sb.append("\t * Erstellt ein neues Objekt der Klasse " + className + " ohne eine Initialisierung der Attribute." + System.lineSeparator());
		sb.append("\t */" + System.lineSeparator());
		sb.append("\tprivate " + className + "() {" + System.lineSeparator());
		sb.append("\t}" + System.lineSeparator());
		sb.append(System.lineSeparator());
		Collection<ViewSpalte> tmpPkSpalten = view.pkSpalten.size() <= 0 ? view.spalten : view.pkSpalten; 
		sb.append(getCode4EqualsAndHashcode(className, tmpPkSpalten));
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append("\t/**" + System.lineSeparator());
		sb.append("\t * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden." + System.lineSeparator());
		sb.append("\t *" + System.lineSeparator());
		sb.append("\t * @return die String-Repräsentation des Objektes" + System.lineSeparator());
		sb.append("\t */" + System.lineSeparator());
		sb.append("\t@Override" + System.lineSeparator());
		sb.append("\tpublic String toString() {" + System.lineSeparator());
		sb.append("\t\treturn \"" + className + "(" + 
				view.spalten.stream()
					.map(spalte -> "" + spalte.name + "=\" + this." + spalte.name + " + \"")
					.collect(Collectors.joining(", "))				
				+ ")\";" + System.lineSeparator());
		sb.append("\t}" + System.lineSeparator());		
		sb.append(System.lineSeparator());
		sb.append("}");
		return sb.toString();
	}
	

	/**
	 * Generiert für die View eine DTO-Klasse für den Primärschlüssel
	 * 
	 * @param rev    die Revision
	 * 
	 * @return der Java-Code für die Primärschlüssel-DTO-Klasse. 
	 */
	public String getCode4PrimaryKeyClass(final long rev) {
		String className = getJavaKlasse(rev);
		// Bestimme die Spalten für den Primärschlüssel und erzeuge nur Code, wenn es sich nicht um einen einfachen Primärschlüssel handelt
		List<ViewSpalte> pkSpalten = view.pkSpalten;
		if (pkSpalten == null)
			pkSpalten = view.spalten;
		if (pkSpalten.size() <= 1)
			return null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("package " + getPackageName(rev) + ";" + System.lineSeparator());
		sb.append(System.lineSeparator());	
		sb.append("import java.io.Serializable;" + System.lineSeparator());
		sb.append(System.lineSeparator());	
		sb.append("/**" + System.lineSeparator());
		sb.append(" * Diese Klasse dient als DTO für den Primärschlüssel der Datenbank-View " + view.name + "." + System.lineSeparator());
		sb.append(" * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, " + System.lineSeparator());
		sb.append(" * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird." + System.lineSeparator());
		sb.append(" */" + System.lineSeparator());
		sb.append("public class " + className + "PK implements Serializable {" + System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append("\t/** Die UID für diese Klasse */" + System.lineSeparator());
		sb.append("\tprivate static final long serialVersionUID = 1L;" + System.lineSeparator());
		sb.append(System.lineSeparator());		
		sb.append(view.pkSpalten.stream()
				.map(col -> getCode4Attributes(col, rev, false))
				.filter(code -> code != null)
				.collect(Collectors.joining(System.lineSeparator())));
		sb.append(System.lineSeparator());		
		sb.append("\t/**" + System.lineSeparator());
		sb.append("\t * Erstellt ein neues Objekt der Klasse " + className + "PK ohne eine Initialisierung der Attribute." + System.lineSeparator());
		sb.append("\t */" + System.lineSeparator());
		sb.append("\t@SuppressWarnings(\"unused\")" + System.lineSeparator());
		sb.append("\tprivate " + className + "PK() {" + System.lineSeparator());
		sb.append("\t}" + System.lineSeparator());
		sb.append(System.lineSeparator());		
		sb.append("\t/**" + System.lineSeparator());
		sb.append("\t * Erstellt ein neues Objekt der Klasse " + className + "PK." + System.lineSeparator());
		view.pkSpalten.stream().forEach(spalte -> {
			sb.append("\t * @param " + spalte.name + "   der Wert für das Attribut " + spalte.name + "" + System.lineSeparator());
		});
		sb.append("\t */" + System.lineSeparator());
		sb.append("\tpublic " + className + "PK(");
		sb.append(view.pkSpalten.stream()
				.map(spalte -> "final " + spalte.datentyp + " " + spalte.name)
				.collect(Collectors.joining(", "))
				);
		sb.append(") {" + System.lineSeparator());
		sb.append(view.pkSpalten.stream()
				.map(spalte ->
				    "\t\tif (" + spalte.name + " == null) { " + System.lineSeparator() +
			        "\t\t\tthrow new NullPointerException(\"" + spalte.name + " must not be null\");" + System.lineSeparator() +
				    "\t\t}" + System.lineSeparator() +
				    "\t\tthis." + spalte.name + " = " + spalte.name + ";" + System.lineSeparator())
				.collect(Collectors.joining())
				);
		sb.append("\t}" + System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append(getCode4EqualsAndHashcode(className + "PK", pkSpalten));
		sb.append("}");
		return sb.toString();
	}
	
}
