package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Carrier Type
 * http://www.loc.gov/marc/bibliographic/bd338.html
 */
public class Tag338 extends DataFieldDefinition {
	private static Tag338 uniqueInstance;

	private Tag338() {
		initialize();
		postCreation();
	}

	public static Tag338 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag338();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "338";
		label = "Carrier Type";
		bibframeTag = "Carrier";
		mqTag = "CarrierType";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd338.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Carrier type term", "R",
			"b", "Carrier type code", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("carrierTypeCode");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setBibframeTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
