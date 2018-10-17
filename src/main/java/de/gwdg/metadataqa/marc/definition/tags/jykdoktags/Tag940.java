package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * http://marc21.kansalliskirjasto.fi/bib/9XX.xml
 */
public class Tag940 extends DataFieldDefinition {

	private static Tag940 uniqueInstance;

	private Tag940() {
		initialize();
		postCreation();
	}

	public static Tag940 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag940();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "940";
		label = "VIITTAUS NIMEKE";
		mqTag = "ViittausNimeke";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://marc21.kansalliskirjasto.fi/bib/9XX.xml#940";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Nimeke", "NR",
			"y", "Viittauksen kohde kirjoitettuna", "NR",
			"6", "Linkitys", "NR",
			"8", "Linkki ja järjestysnumero", "T"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
