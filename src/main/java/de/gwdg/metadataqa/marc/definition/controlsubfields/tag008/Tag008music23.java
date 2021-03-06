package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music23 extends ControlSubfieldDefinition {
	private static Tag008music23 uniqueInstance;

	private Tag008music23() {
		initialize();
		extractValidCodes();
	}

	public static Tag008music23 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008music23();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of item";
		id = "tag008music23";
		mqTag = "formOfItem";
		positionStart = 23;
		positionEnd = 24;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
		codes = Utils.generateCodes(
			" ", "None of the following",
			"a", "Microfilm",
			"b", "Microfiche",
			"c", "Microopaque",
			"d", "Large print",
			"f", "Braille",
			"o", "Online",
			"q", "Direct electronic",
			"r", "Regular print reproduction",
			"s", "Electronic",
			"|", "No attempt to code"
		);

		historicalCodes = Utils.generateCodes(
			"g", "Punched paper tape [OBSOLETE, 1987]",
			"h", "Magnetic tape [OBSOLETE, 1987]",
			"i", "Multimedia [OBSOLETE, 1987]",
			"x", "Other form of reproduction [OBSOLETE] [USMARC only]",
			"z", "Other form of reproduction [OBSOLETE]"
		);
	}
}