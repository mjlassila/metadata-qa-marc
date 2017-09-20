package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Thematic Index Code Source Codes
 * http://www.loc.gov/standards/sourcelist/thematic-index.html
 */
public class ThematicIndexCodeSourceCodes extends CodeList {

	static {
		codes = Utils.generateCodes(
				"mlati", "Thematic Indexes Used in the Library of Congress/NACO Authority File (Compiled by the Music Library Association)"
		);
		indexCodes();
	}

	private static ThematicIndexCodeSourceCodes uniqueInstance;

	private ThematicIndexCodeSourceCodes() {}

	public static ThematicIndexCodeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ThematicIndexCodeSourceCodes();
		return uniqueInstance;
	}
}