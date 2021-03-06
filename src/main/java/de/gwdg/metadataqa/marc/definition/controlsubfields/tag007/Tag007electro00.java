package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro00 extends ControlSubfieldDefinition {
	private static Tag007electro00 uniqueInstance;

	private Tag007electro00() {
		initialize();
		extractValidCodes();
	}

	public static Tag007electro00 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007electro00();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Category of material";
		id = "tag007electro00";
		mqTag = "categoryOfMaterial";
		positionStart = 0;
		positionEnd = 1;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
		codes = Utils.generateCodes(
			"c", "Electronic resource"
		);
	}
}