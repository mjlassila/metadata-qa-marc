package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Price Type Code Source Codes
 * http://www.loc.gov/standards/sourcelist/price-type.html
 */
public class PriceTypeCodeSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"onixpt", "ONIX Price Type Codes List number 58"
		);
		indexCodes();
	}

	private static PriceTypeCodeSourceCodes uniqueInstance;

	private PriceTypeCodeSourceCodes() {
		initialize();
	}

	public static PriceTypeCodeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new PriceTypeCodeSourceCodes();
		return uniqueInstance;
	}
}
