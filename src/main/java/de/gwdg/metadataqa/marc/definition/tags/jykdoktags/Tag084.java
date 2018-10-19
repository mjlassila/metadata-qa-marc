package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Other Classificaton Number
 * http://marc21.kansalliskirjasto.fi/bib/05X-08X.htm#084
 */
public class Tag084 extends DataFieldDefinition {

	private static Tag084 uniqueInstance;

	private Tag084() {
		initialize();
		postCreation();
	}

	public static Tag084 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag084();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "084";
		label = "Muu luokitus";
		bibframeTag = "Classification";
		// mqTag = "OtherClassificatonNumber";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd084.html";

		ind1 = new Indicator("Suomalainen indikaattori").setCodes(
				" ", "Määrittelemätön",
				"9", "YKL-luokituksen fiktiivisen aineiston lisäluokka"
			)
			.setMqTag("tyyppi");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Luokka", "R",
			"b", "Kappalenumero", "NR",
			"q", "Luokittava organisaatio", "NR",
			"0", "Auktoriteettitietueen kontrollinumero tai standardinumero", "R",
			"1", "Reaalimaailman kohteen tunniste", "R",
			"2", "Luokituksen lähde", "NR",
			"6", "Linkitys", "NR",
			"8", "Linkki- ja järjestysnumero", "R"
		);

		getSubfield("q").setCodeList(OrganizationCodes.getInstance());
		getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("classificationPortion");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("q").setBibframeTag("assigner");
		getSubfield("2").setBibframeTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
