package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#971
 */
public class Tag591 extends DataFieldDefinition {

	private static Tag591 uniqueInstance;

	private Tag591() {
		initialize();
		postCreation();
	}

	public static Tag591 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag591();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "591";
		label = "JULKAISULUETTELO";
		mqTag = "Julkaisuluettelo";
		cardinality = Cardinality.Repeatable;

		setSubfieldsWithCardinality(
			"a", "Vastuualuekoodi/laitostunnus", "NR",
			"b", "Julkaisun tekijä/t", "NR",
			"c", "Projektitunnus/-tunnukset", "NR",
			"d", "AMKOTA- tai KOTA-luokitus", "NR",
			"e", "Impact-arvo", "NR",
			"f", "Koulutusala", "NR",
			"g", "Henkilökuntakoodi", "NR",
			"h", "Yleinen huomautus", "NR",
			"i", "Tietenalaluokitus", "NR",
			"j", "Julkaisuluetteloviite", "NR",
			"k", "Reference of publication list", "NR",
			"m", "Open access -koodi", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("i").setMqTag("rdf:value");
	}
}
