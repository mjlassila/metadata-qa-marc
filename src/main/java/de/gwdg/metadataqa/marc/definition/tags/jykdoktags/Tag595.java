package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag595 extends DataFieldDefinition {

	private static Tag595 uniqueInstance;

	private Tag595() {
		initialize();
		postCreation();
	}

	public static Tag595 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag595();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "595";
		label = "LISÄPAINOSTIEDOT";
		mqTag = "Lisäpainostiedot";
		cardinality = Cardinality.Nonrepeatable;

		setSubfieldsWithCardinality(
			"a", "Kirjaus lisäpainoksesta", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("a").setMqTag("rdf:value");
	}
}
