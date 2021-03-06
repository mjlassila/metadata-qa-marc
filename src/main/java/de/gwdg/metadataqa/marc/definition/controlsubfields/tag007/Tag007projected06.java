package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Medium for sound
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected06 extends ControlSubfieldDefinition {
	private static Tag007projected06 uniqueInstance;

	private Tag007projected06() {
		initialize();
		extractValidCodes();
	}

	public static Tag007projected06 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007projected06();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Medium for sound";
		id = "tag007projected06";
		mqTag = "mediumForSound";
		positionStart = 6;
		positionEnd = 7;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
		codes = Utils.generateCodes(
			" ", "No sound (silent)",
			"a", "Optical sound track on motion picture film",
			"b", "Magnetic sound track on motion picture film",
			"c", "Magnetic audio tape in cartridge",
			"d", "Sound disc",
			"e", "Magnetic audio tape on reel",
			"f", "Magnetic audio tape in cassette",
			"g", "Optical and magnetic sound track on motion picture film",
			"h", "Videotape",
			"i", "Videodisc",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
		historicalCodes = Utils.generateCodes(
			"g", "Other [OBSOLETE, 1981]"
		);
	}
}