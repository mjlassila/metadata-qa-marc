package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag850 extends DataFieldDefinition {

	private static Tag850 uniqueInstance;

	private Tag850() {
		initialize();
		postCreation();
	}

	public static Tag850 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag850();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "850";
		label = "KIRJASTOTUNNUS MONOGRAFIAOSAKOHTEISSA";
		mqTag = "KirjastotunnusMonoOsakohteessa";
		cardinality = Cardinality.Nonrepeatable;

		setSubfieldsWithCardinality(
			"a", "Kirjastotunnus monografiaosakohteessa", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("a").setMqTag("rdf:value");
	}
}
