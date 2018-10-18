package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Jyväskylä University Library
 */
public class Tag593 extends DataFieldDefinition {

	private static Tag593 uniqueInstance;

	private Tag593() {
		initialize();
		postCreation();
	}

	public static Tag593 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag593();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "593";
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