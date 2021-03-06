package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Type of Report and Period Covered Note
 * http://www.loc.gov/marc/bibliographic/bd513.html
 */
public class Tag513 extends DataFieldDefinition {

	private static Tag513 uniqueInstance;

	private Tag513() {
		initialize();
		postCreation();
	}

	public static Tag513 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag513();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "513";
		label = "Type of Report and Period Covered Note";
		bibframeTag = "TypeOfReport";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd513.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Type of report", "NR",
			"b", "Period covered", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("period");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
