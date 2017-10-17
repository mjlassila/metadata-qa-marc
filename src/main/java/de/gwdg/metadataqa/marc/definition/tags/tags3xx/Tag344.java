package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Sound Characteristics
 * http://www.loc.gov/marc/bibliographic/bd344.html
 */
public class Tag344 extends DataFieldDefinition {
	private static Tag344 uniqueInstance;

	private Tag344() {
		initialize();
		postCreation();
	}

	public static Tag344 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag344();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "344";
		label = "Sound Characteristics";
		bibframeTag = "SoundCharacteristic";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Type of recording", "R",
			"b", "Recording medium", "R",
			"c", "Playing speed", "R",
			"d", "Groove characteristic", "R",
			"e", "Track configuration", "R",
			"f", "Tape configuration", "R",
			"g", "Configuration of playback channels", "R",
			"h", "Special playback characteristics", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("recordingMethod");
		getSubfield("b").setBibframeTag("recordingMedium");
		getSubfield("c").setBibframeTag("playingSpeed");
		getSubfield("d").setBibframeTag("grooveCharacteristics");
		getSubfield("e").setBibframeTag("trackConfig");
		getSubfield("f").setBibframeTag("tapeConfig");
		getSubfield("g").setBibframeTag("playbackChannels");
		getSubfield("h").setBibframeTag("playbackCharacteristic");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setBibframeTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
