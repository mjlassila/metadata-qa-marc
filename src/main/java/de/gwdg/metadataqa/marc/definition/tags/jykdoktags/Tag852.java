package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag852 extends DataFieldDefinition {

	private static Tag852 uniqueInstance;

	private Tag852() {
		initialize();
		postCreation();
	}

	public static Tag852 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag852();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "852";
		label = "JYU SIGNUM";
		mqTag = "JyuSignum";
		cardinality = Cardinality.Repeatable;

		setSubfieldsWithCardinality(
			"a", "Sijainti", "NR",
			"b", "Alayksikkö tai kokoelma", "R",
			"c", "Sijainti hyllyssä", "R",
			"t", "Kappaleen numero", "NR",
			"h", "Paikkamerkin hyllyluokka", "NR"
		);

		ind1 = new Indicator("Hyllyluokitus").setCodes(
				" ", "Ei määritetty",
				"8", "Muu järjestelmä"
			)
			.setMqTag("level");

		ind2 = new Indicator();

		getSubfield("c").setMqTag("rdf:value");
	}
}
