package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.FormatSourceCodes;

/**
 * METALIB-database record
 * https://docplayer.fi/44866159-Metalibin-tietokantatietueiden-konversio-voyager-kayttoon.html
 */
public class Tag886 extends DataFieldDefinition {

	private static Tag886 uniqueInstance;

	private Tag886() {
		initialize();
		postCreation();
	}

	public static Tag886 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag886();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "886";
		label = "METALIB-tietue";
		mqTag = "MetalibField";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd886.html";

		ind1 = new Indicator("Type of field")
			.setCodes(
				"2", "Metalib field"
			)
			.setMqTag("typeOfField");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Käyttö", "NR",
			"2", "Paikallisuus", "NR",
			"b", "Numerokoodi", "NR",
			"c", "Arvo", "NR",
			"5", "ISIL-tunnus", "NR",
			"8", "Linkitysnumero", "NR"
		);

		
	}
}
