package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TagDefinitionLoader {

	private static final Logger logger = Logger.getLogger(TagDefinitionLoader.class.getCanonicalName());

	private static final Pattern PATTERN_20x = Pattern.compile("^2[0-4]\\d$");
	private static final Pattern PATTERN_25x = Pattern.compile("^2[5-9]\\d$");
	private static final Pattern PATTERN_70x = Pattern.compile("^7[0-5]\\d$");
	private static final Pattern PATTERN_76x = Pattern.compile("^7[6-9]\\d$");
	private static final Pattern PATTERN_80x = Pattern.compile("^8[0-3]\\d$");
	private static final Pattern PATTERN_84x = Pattern.compile("^8[4-9]\\d$");

	private static final List<String> OCLC_TAGS = Arrays.asList("012", "019", "029", "090", "092", "096", "366", "539",
		"891", "911", "912", "936", "938", "994");
	private static Map<String, DataFieldDefinition> commonCache = new HashMap<>();
	private static Map<String, Map<MarcVersion, DataFieldDefinition>> versionedCache = new HashMap<>();

	static {
		findAndCacheTags();
	}

	private static void findAndCacheTags() {
		List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
		for (Class<? extends DataFieldDefinition> definitionClazz : tags) {
			loadAndCacheTag(definitionClazz);
		}
	}

	private static void loadAndCacheTag(Class<? extends DataFieldDefinition> definitionClazz) {
		DataFieldDefinition dataFieldDefinition = null;
		Method getInstance = null;
		try {
			getInstance = definitionClazz.getMethod("getInstance");
			dataFieldDefinition = (DataFieldDefinition) getInstance.invoke(definitionClazz);
			if (dataFieldDefinition != null) {
				String tag = dataFieldDefinition.getTag();
				commonCache.put(tag, dataFieldDefinition);
				MarcVersion version = getMarcVersion(definitionClazz);
				if (!versionedCache.containsKey(tag)) {
					versionedCache.put(tag, new HashMap<>());
				}
				versionedCache.get(tag).put(version, dataFieldDefinition);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static MarcVersion getMarcVersion(Class<? extends DataFieldDefinition> definitionClazz) {
		MarcVersion version = MarcVersion.MARC21;
		if (definitionClazz.getCanonicalName().contains(".oclctags.")) {
			version = MarcVersion.OCLC;
		} else if (definitionClazz.getCanonicalName().contains(".dnbtags.")) {
			version = MarcVersion.DNB;
		} else if (definitionClazz.getCanonicalName().contains(".genttags.")) {
			version = MarcVersion.GENT;
		} else if (definitionClazz.getCanonicalName().contains(".sztetags.")) {
			version = MarcVersion.SZTE;
		} else if (definitionClazz.getCanonicalName().contains(".fennicatags.")) {
			version = MarcVersion.FENNICA;
		} else if (definitionClazz.getCanonicalName().contains(".jykdoktags.")) {
			version = MarcVersion.JYKDOK;
		}
		return version;
	}

	public static DataFieldDefinition load(String tag) {
		if (!commonCache.containsKey(tag)) {
			DataFieldDefinition dataFieldDefinition = null;
			try {
				String className = getClassName(tag);
				if (className != null) {
					Class definitionClazz = Class.forName(className);
					Method getInstance = definitionClazz.getMethod("getInstance");
					dataFieldDefinition = (DataFieldDefinition) getInstance.invoke(definitionClazz);
				}
			} catch (ClassNotFoundException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			commonCache.put(tag, dataFieldDefinition);
		}
		return commonCache.get(tag);
	}

	public static DataFieldDefinition load(String tag, MarcVersion marcVersion) {
		Map<MarcVersion, DataFieldDefinition> map = versionedCache.get(tag);

		if (map == null)
			return null;

		if (map.containsKey(marcVersion)) {
			return map.get(marcVersion);
		}
		if (marcVersion.equals(MarcVersion.MARC21)) {
			// no fallback for MARC21
			return null;
		} else {
			// fallbacks for other MARC versions
			if (map.containsKey(MarcVersion.MARC21))
				return map.get(MarcVersion.MARC21);
			if (map.containsKey(MarcVersion.OCLC))
				return map.get(MarcVersion.OCLC);
		}
		return null;
	}

	public static String getClassName(String tag) {
		String packageName = null;
		if (OCLC_TAGS.contains(tag)) {
			packageName = "oclctags";
		} else if (tag.startsWith("0")) {
			packageName = "tags01x";
		} else if (tag.startsWith("1")) {
			packageName = "tags1xx";
		} else if (PATTERN_20x.matcher(tag).matches()) {
			packageName = "tags20x";
		} else if (PATTERN_25x.matcher(tag).matches()) {
			packageName = "tags25x";
		} else if (tag.startsWith("3")) {
			packageName = "tags3xx";
		} else if (tag.startsWith("4")) {
			packageName = "tags4xx";
		} else if (tag.startsWith("5")) {
			packageName = "tags5xx";
		} else if (tag.startsWith("6")) {
			packageName = "tags6xx";
		} else if (PATTERN_70x.matcher(tag).matches()) {
			packageName = "tags70x";
		} else if (PATTERN_76x.matcher(tag).matches()) {
			packageName = "tags76x";
		} else if (PATTERN_80x.matcher(tag).matches()) {
			packageName = "tags80x";
		} else if (PATTERN_84x.matcher(tag).matches()) {
			packageName = "tags84x";
		}

		if (packageName == null)
			return null;

		return String.format("de.gwdg.metadataqa.marc.definition.tags.%s.Tag%s", packageName, tag);
	}
}
