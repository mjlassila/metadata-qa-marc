package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * http://marc21.kansalliskirjasto.fi/bib/9XX.xml
 */
public class Tag910 extends DataFieldDefinition {

	private static Tag910 uniqueInstance;

	private Tag910() {
		initialize();
		postCreation();
	}

	public static Tag910 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag910();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "910";
		label = "VIITTAUS YHTEISÖNIMI";
		mqTag = "ViittausYhteisönimi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://marc21.kansalliskirjasto.fi/bib/9XX.xml#910";


		setSubfieldsWithCardinality(
			"a", "Pääyhteisö", "NR",
			"b", "Alayhteisö", "R",
			"c", "Kokouksen paikka", "NR",
			"d", "Kokouksen aika", "NR",
			"g", "Muut nimeen tehtävät lisäykset", "NR",
			"n", "Kokouksen järjestysnumero", "NR",
			"t", "Nimeke", "NR",
			"y", "Viittauksen kohde kirjoitettuna", "NR",
			"6", "Linkitys", "NR",
			"8", "Linkki ja järjestysnumero", "NR"
			
		);

		ind1 = new Indicator("Nimen tyyppi").setCodes(
				" ", "Määrittelemätön",
				"1", "Valtion tai hallintoalueen nimi",
				"2", "Muu yhteisö"
			)
			.setMqTag("tyyppi");

		ind2 = new Indicator("Viittauksen tyyppi").setCodes(
				" ", "Katso-viittaus",
				"1", "Katso myös -viittaus"
			)
			.setMqTag("viittaustyyppi");

		getSubfield("a").setMqTag("rdf:value");
	}
}
