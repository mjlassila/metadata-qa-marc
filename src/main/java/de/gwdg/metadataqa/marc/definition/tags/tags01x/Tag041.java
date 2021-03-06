package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Language Code
 * http://www.loc.gov/marc/bibliographic/bd041.html
 */
public class Tag041 extends DataFieldDefinition {

	private static Tag041 uniqueInstance;

	private Tag041() {
		initialize();
		postCreation();
	}

	public static Tag041 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag041();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "041";
		label = "Language Code";
		bibframeTag = "Language";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd041.html";

		ind1 = new Indicator("Translation indication")
			.setCodes(
				" ", "No information provided",
				"0", "Item not a translation/does not include a translation",
				"1", "Item is or includes a translation"
			)
			.setMqTag("translationIndication");
		ind2 = new Indicator("Source of code")
			.setCodes(
				" ", "MARC language code",
				"7", "Source specified in subfield $2"
			)
			.setMqTag("sourceOfCode");

		setSubfieldsWithCardinality(
			"a", "Language code of text/sound track or separate title", "R",
			"b", "Language code of summary or abstract", "R",
			"d", "Language code of sung or spoken text", "R",
			"e", "Language code of librettos", "R",
			"f", "Language code of table of contents", "R",
			"g", "Language code of accompanying material other than librettos", "R",
			"h", "Language code of original", "R",
			"j", "Language code of subtitles or captions", "R",
			"k", "Language code of intermediate translations", "R",
			"m", "Language code of original accompanying materials other than librettos", "R",
			"n", "Language code of original libretto", "R",
			"2", "Source of code", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setCodeList(LanguageCodes.getInstance());
		getSubfield("b").setCodeList(LanguageCodes.getInstance());
		getSubfield("d").setCodeList(LanguageCodes.getInstance());
		getSubfield("e").setCodeList(LanguageCodes.getInstance());
		getSubfield("f").setCodeList(LanguageCodes.getInstance());
		getSubfield("g").setCodeList(LanguageCodes.getInstance());
		getSubfield("h").setCodeList(LanguageCodes.getInstance());
		getSubfield("j").setCodeList(LanguageCodes.getInstance());
		getSubfield("k").setCodeList(LanguageCodes.getInstance());
		getSubfield("m").setCodeList(LanguageCodes.getInstance());
		getSubfield("n").setCodeList(LanguageCodes.getInstance());
		getSubfield("2").setCodeList(LanguageCodeAndTermSourceCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("b").setMqTag("ofSummary");
		getSubfield("d").setMqTag("ofSungOrSpokenText");
		getSubfield("e").setMqTag("ofLibrettos");
		getSubfield("f").setMqTag("ofTableOfContents");
		getSubfield("g").setMqTag("ofAccompanyingMaterial");
		getSubfield("h").setMqTag("ofOriginal");
		getSubfield("j").setMqTag("ofSubtitles");
		getSubfield("k").setMqTag("ofTranslations");
		getSubfield("m").setMqTag("ofOriginalAccompanyingMaterial");
		getSubfield("n").setMqTag("ofOriginalLibretto");
		getSubfield("2").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"c", "Languages of separate titles (VM) [OBSOLETE, 1972], Languages of available translation (SE) [OBSOLETE, 1977]"
		);
	}
}
