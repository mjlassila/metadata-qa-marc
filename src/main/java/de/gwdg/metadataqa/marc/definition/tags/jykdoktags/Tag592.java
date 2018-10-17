package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://kirjasto.jyu.fi/intra/kokoelmat/luettelointi/kirjastokohtaisetkentat
 */
public class Tag592 extends DataFieldDefinition {

	private static Tag592 uniqueInstance;

	private Tag592() {
		initialize();
		postCreation();
	}

	public static Tag592 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag592();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "592";
		label = "E-KIRJOJEN ASIAKASLÄHTÖINEN OSTAUTUMINEN";
		mqTag = "EKirjojenAsiakasOstautuminen";
		cardinality = Cardinality.Nonrepeatable;

		setSubfieldsWithCardinality(
			"a", "e-kirjojen asiakaslähtöinen ostautuminen", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("a").setMqTag("rdf:value");
	}
}
