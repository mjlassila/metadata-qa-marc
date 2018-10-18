package de.gwdg.metadataqa.marc.definition.tags.jykdoktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * http://marc21.kansalliskirjasto.fi/bib/01X-04X.htm#039
 */
public class Tag039 extends DataFieldDefinition {

	private static Tag039 uniqueInstance;

	private Tag039() {
		initialize();
		postCreation();
	}

	public static Tag039 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag039();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "039";
		label = "SUOMALAINEN KARTASTOKOORDINAATTIJÄRJESTELMÄ";
		mqTag = "SuomalainenKartastokoordinaatti";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "http://marc21.kansalliskirjasto.fi/bib/01X-04X.htm#039";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Karttalehden numero. Merkitään yleislehtijakoon perustuvat lehtinumerot", "R",
			"b", "Koordinaattijärjestelmä (kkj)", "NR",
			"d", "Läntisin koordinaatti", "NR",
			"e", "Itäisin koordinaatti", "NR",
			"f", "Pohjoisin koordinaatti", "NR",
			"g", "Eteläisin koordinaatti", "NR",
			"6", "Linkitys", "NR",
			"8", "Linkki- ja järjestysnumero", "R"
		);

		getSubfield("b").setCodes(
			"a", "peruskoordinaattijärjestelmä (pkj)",
			"b", "yhtenäiskoordinaattijärjestelmä (ykj",
			"c", "muu järjestelmä",
			"d", "EUREF-FIN"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

	}
}