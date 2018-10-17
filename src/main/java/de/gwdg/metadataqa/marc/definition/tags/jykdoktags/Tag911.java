package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * http://marc21.kansalliskirjasto.fi/bib/9XX.xml
 */
public class Tag911 extends DataFieldDefinition {

	private static Tag911 uniqueInstance;

	private Tag911() {
		initialize();
		postCreation();
	}

	public static Tag911 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag911();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "911";
		label = "VIITTAUS KOKOUKSEN NIMI";
		mqTag = "ViittausKokouksenNimi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://marc21.kansalliskirjasto.fi/bib/9XX.xml#911";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Kongressin nimi", "NR",
			"c", "Kongressin paikka", "NR",
			"d", "Kongressin aika", "NR",
			"e", "Korgressin alaosasto", "NR",
			"g", "Muut nimeen tehtävät lisäykset", "NR",
			"n", "Kongressin järjestysnumero", "NR",
			"t", "Nimeke", "NR",
			"y", "Viittauksen kohde kirjoitettuna", "NR",
			"6", "Linkitys", "NR",
			"8", "Linkki- ja järjestysnumero", "T"
			
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
