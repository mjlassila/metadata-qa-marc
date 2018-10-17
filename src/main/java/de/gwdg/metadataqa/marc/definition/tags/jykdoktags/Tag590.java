package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag590 extends DataFieldDefinition {

	private static Tag590 uniqueInstance;

	private Tag590() {
		initialize();
		postCreation();
	}

	public static Tag590 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag590();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "590";
		label = "PÄÄKIRJASTON HUOMAUTUSKENTTÄ";
		mqTag = "PääkirjastonHuomautus";
		cardinality = Cardinality.Repeatable;

		setSubfieldsWithCardinality(
			"a", "Pääkirjaston huomautuskenttä", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("a").setMqTag("rdf:value");
	}
}
