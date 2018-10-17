package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#971
 */
public class Tag509 extends DataFieldDefinition {

	private static Tag509 uniqueInstance;

	private Tag509() {
		initialize();
		postCreation();
	}

	public static Tag509 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag509();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "509";
		label = "OPINNÄYTE";
		mqTag = "Opinnäyte";
		cardinality = Cardinality.Repeatable;

		setSubfieldsWithCardinality(
			"a", "Opinnäytteen tyyppi", "NR",
			"c", "Korkeakoulu, laitos ja oppiaine", "NR",
			"d", "Opinnäytteen hyväksymisvuosi", "NR",
			"9", "Oppiainekoodi", "NR"
		);

		ind1 = new Indicator();
		ind2 = new Indicator();

		getSubfield("c").setMqTag("rdf:value");
	}
}
