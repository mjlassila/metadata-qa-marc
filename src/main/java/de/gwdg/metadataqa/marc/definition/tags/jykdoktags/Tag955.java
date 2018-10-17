package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag955 extends DataFieldDefinition {

	private static Tag955 uniqueInstance;

	private Tag955() {
		initialize();
		postCreation();
	}

	public static Tag955 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag955();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "955";
		label = "EMOTIETUEEN SIJAINTI";
		mqTag = "EmotietueenSijainti";
		cardinality = Cardinality.Nonrepeatable;

		setSubfieldsWithCardinality(
			"c", "Musiikin laitoksen emotietueen sijainti", "NR",
			"d", "Tarranumero", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("c").setMqTag("rdf:value");
	}
}
