package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Jyväskylä University Library
 */
public class Tag594 extends DataFieldDefinition {

	private static Tag594 uniqueInstance;

	private Tag594() {
		initialize();
		postCreation();
	}

	public static Tag594 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag594();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "594";
		label = "JYKDOK-huomautus";
		mqTag = "JykdokHuomautus";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Huomautus", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}