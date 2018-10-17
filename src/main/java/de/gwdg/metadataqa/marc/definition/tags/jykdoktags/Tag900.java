package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * http://marc21.kansalliskirjasto.fi/bib/9XX.xml
 */
public class Tag900 extends DataFieldDefinition {

	private static Tag900 uniqueInstance;

	private Tag900() {
		initialize();
		postCreation();
	}

	public static Tag900 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag900();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "900";
		label = "VIITTAUS HENKILÖNIMI";
		mqTag = "ViittausHenkilönimi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://marc21.kansalliskirjasto.fi/bib/9XX.xml#900";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Sukunimi, Etunimi", "NR",
			"c", "Muut nimeen tehtävät lisäykset", "NR",
			"d", "Aikamääreet", "NR",
			"t", "Nimeke", "NR",
			"y", "Viittauksen kohde kirjoitettuna", "NR",
			"6", "Linkitys", "NR",
			"8", "Linkki- ja järjestysnumero", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
