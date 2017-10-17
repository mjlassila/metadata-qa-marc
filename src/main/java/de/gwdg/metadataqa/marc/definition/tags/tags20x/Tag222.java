package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Key Title
 * http://www.loc.gov/marc/bibliographic/bd222.html
 */
public class Tag222 extends DataFieldDefinition {
	private static Tag222 uniqueInstance;

	private Tag222() {
		initialize();
		postCreation();
	}

	public static Tag222 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag222();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "222";
		label = "Key Title";
		bibframeTag = "KeyTitle";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator("Nonfiling characters").setCodes(
			"0", "No nonfiling characters",
			"1-9", "Number of nonfiling characters"
		).setMqTag("nonfilingCharacters");
		ind2.getCode("1-9").setRange(true);

		setSubfieldsWithCardinality(
			"a", "Key title", "NR",
			"b", "Qualifying information", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("mainTitle");
		getSubfield("b").setBibframeTag("qualifier");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
