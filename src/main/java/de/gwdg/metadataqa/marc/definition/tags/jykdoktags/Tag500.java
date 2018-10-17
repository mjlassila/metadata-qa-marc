package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag500 extends DataFieldDefinition {

	private static Tag500 uniqueInstance;

	private Tag500() {
		initialize();
		postCreation();
	}

	public static Tag500 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag500();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "500";
		label = "OPINTOVAATIMUSARTIKKELI";
		mqTag = "ArtikkeliOpintovaatimuksissa";
		cardinality = Cardinality.Repeatable;

		setSubfieldsWithCardinality(
			"a", "Huomautus artikkelista opintovaatimuksissa", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("a").setMqTag("rdf:value");
	}
}
