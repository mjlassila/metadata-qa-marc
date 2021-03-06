package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.validator.ClassificationReferenceValidator;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarcRecord implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(MarcRecord.class.getCanonicalName());
	private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
	private static final Pattern positionalPattern = Pattern.compile("^(Leader|00[678])/(.*)$");
	private static final List<String> simpleControlTags = Arrays.asList("001", "003", "005");

	private Leader leader;
	private MarcControlField control001;
	private MarcControlField control003;
	private MarcControlField control005;
	private Control006 control006;
	private Control007 control007;
	private Control008 control008;
	private List<DataField> datafields;
	private Map<String, List<DataField>> datafieldIndex;
	private Map<String, List<MarcControlField>> controlfieldIndex;
	Map<String, List<String>> mainKeyValuePairs;
	private List<String> errors = null;
	private List<ValidationError> validationErrors = null;

	private List<String> unhandledTags;

	public MarcRecord() {
		datafields = new ArrayList<>();
		datafieldIndex = new TreeMap<>();
		controlfieldIndex = new TreeMap<>();
		unhandledTags = new ArrayList<>();
	}

	public MarcRecord(String id) {
		this();
		control001 = new MarcControlField(Control001Definition.getInstance(), id);
	}

	public void addDataField(DataField dataField) {
		dataField.setRecord(this);
		indexField(dataField);
		datafields.add(dataField);
	}

	private void indexField(DataField dataField) {
		String tag = dataField.getTag();

		if (!datafieldIndex.containsKey(tag))
			datafieldIndex.put(tag, new ArrayList<>());

		datafieldIndex.get(tag).add(dataField);
	}

	public void addUnhandledTags(String tag) {
		unhandledTags.add(tag);
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
		leader.setMarcRecord(this);
	}

	public Leader getLeader() {
		return leader;
	}

	public Leader.Type getType() {
		return leader.getType();
	}

	public MarcControlField getControl001() {
		return control001;
	}

	public MarcRecord setControl001(MarcControlField control001) {
		this.control001 = control001;
		controlfieldIndex.put(control001.definition.getTag(), Arrays.asList(control001));
		return this;
	}

	public MarcControlField getControl003() {
		return control003;
	}

	public void setControl003(MarcControlField control003) {
		this.control003 = control003;
		controlfieldIndex.put(control003.definition.getTag(), Arrays.asList(control003));
	}

	public MarcControlField getControl005() {
		return control005;
	}

	public void setControl005(MarcControlField control005) {
		this.control005 = control005;
		controlfieldIndex.put(control005.definition.getTag(), Arrays.asList(control005));
	}

	public Control006 getControl006() {
		return control006;
	}

	public void setControl006(Control006 control006) {
		this.control006 = control006;
		control006.setMarcRecord(this);
		controlfieldIndex.put(control006.definition.getTag(), Arrays.asList(control006));
	}

	public Control007 getControl007() {
		return control007;
	}

	public void setControl007(Control007 control007) {
		this.control007 = control007;
		control007.setMarcRecord(this);
		controlfieldIndex.put(control007.definition.getTag(), Arrays.asList(control007));
	}

	public Control008 getControl008() {
		return control008;
	}

	public void setControl008(Control008 control008) {
		this.control008 = control008;
		control008.setMarcRecord(this);
		controlfieldIndex.put(control008.definition.getTag(), Arrays.asList(control008));
	}

	public String getId() {
		return control001.getContent();
	}

	private List<MarcControlField> getControlfields() {
		return Arrays.asList(
			control001, control003, control005, control006, control007, control008);
	}

	public List<DataField> getDatafield(String tag) {
		return datafieldIndex.getOrDefault(tag, null);
	}

	public boolean exists(String tag) {
		List<DataField> fields = getDatafield(tag);
		return (fields != null && !fields.isEmpty());
	}

	public List<String> getUnhandledTags() {
		return unhandledTags;
	}

	public String format() {
		String output = "";
		for (DataField field : datafields) {
			output += field.format();
		}
		return output;
	}

	public String formatAsMarc() {
		String output = "";
		for (DataField field : datafields) {
			output += field.formatAsMarc();
		}
		return output;
	}

	public String formatForIndex() {
		String output = "";
		for (DataField field : datafields) {
			output += field.formatForIndex();
		}
		return output;
	}

	public Map<String, List<String>> getKeyValuePairs() {
		return getKeyValuePairs(SolrFieldType.MARC);
	}

	public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
		if (mainKeyValuePairs == null) {
			mainKeyValuePairs = new LinkedHashMap<>();

			mainKeyValuePairs.put("type", Arrays.asList(getType().getValue()));
			mainKeyValuePairs.putAll(leader.getKeyValuePairs(type));

			for (MarcControlField controlField : getControlfields()) {
				if (controlField != null)
					mainKeyValuePairs.putAll(controlField.getKeyValuePairs(type));
			}

			for (DataField field : datafields) {
				Map<String, List<String>> keyValuePairs = field.getKeyValuePairs(type);
				mainKeyValuePairs.putAll(keyValuePairs);
			}
		}

		return mainKeyValuePairs;
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		return validate(marcVersion, false);
	}

	public boolean validate(MarcVersion marcVersion, boolean isSummary) {
		errors = new ArrayList<>();
		validationErrors = new ArrayList<>();
		boolean isValidRecord = true;
		boolean isValidComponent;

		isValidComponent = leader.validate(marcVersion);
		if (!isValidComponent) {
			errors.addAll(leader.getErrors());
			List<ValidationError> leaderErrors = leader.getValidationErrors();
			for (ValidationError leaderError : leaderErrors)
				if (leaderError.getRecordId() == null)
					leaderError.setRecordId(getId());
			validationErrors.addAll(leaderErrors);
			isValidRecord = isValidComponent;
		}

		if (!unhandledTags.isEmpty()) {
			if (isSummary) {
				for (String tag : unhandledTags) {
					validationErrors.add(
						new ValidationError(getId(), tag,
							ValidationErrorType.UndefinedField, tag, null));
					errors.add(String.format("Unhandled tag: %s", tag));
				}
			} else {
				Map<String, Integer> tags = new LinkedHashMap<>();
				for (String tag : unhandledTags) {
					if (!tags.containsKey(tag))
						tags.put(tag, 0);
					tags.put(tag, tags.get(tag) + 1);
				}
				List<String> unhandledTagsList = new ArrayList<>();
				for (String tag : tags.keySet()) {
					if (tags.get(tag) == 1)
						unhandledTagsList.add(tag);
					else
						unhandledTagsList.add(String.format("%s (%d*)", tag, tags.get(tag)));
				}
				for (String tag : unhandledTagsList) {
					validationErrors.add(new ValidationError(
						getId(), tag, ValidationErrorType.UndefinedField, tag, null));
					errors.add(String.format("Unhandled tag: %s", tag));
				}
			}

			isValidRecord = false;
		}

		// TODO: use reflection to get all validator class
		ValidatorResponse validatorResponse;
		/*
		validatorResponse = ClassificationReferenceValidator.validate(this);
		if (!validatorResponse.isValid()) {
			errors.addAll(validatorResponse.getErrors());
			isValidRecord = false;
		}
		*/

		for (MarcControlField controlField : getControlfields()) {
			if (controlField != null) {
				// System.err.println(controlField.definition.getTag());
				isValidComponent = ((Validatable)controlField).validate(marcVersion);
				if (!isValidComponent) {
					validationErrors.addAll(((Validatable)controlField).getValidationErrors());
					errors.addAll(((Validatable)controlField).getErrors());
					isValidRecord = isValidComponent;
				}
			}
		}

		Map<DataFieldDefinition, Integer> repetitionCounter = new HashMap<>();
		for (DataField field : datafields) {
			if (!repetitionCounter.containsKey(field.getDefinition())) {
				repetitionCounter.put(field.getDefinition(), 0);
			}
			repetitionCounter.put(field.getDefinition(), repetitionCounter.get(field.getDefinition()) + 1);
			if (!field.validate(marcVersion)) {
				isValidRecord = false;
				validationErrors.addAll(field.getValidationErrors());
				errors.addAll(field.getErrors());
			}

			validatorResponse = ClassificationReferenceValidator.validate(field);
			if (!validatorResponse.isValid()) {
				validationErrors.addAll(validatorResponse.getValidationErrors());
				errors.addAll(validatorResponse.getErrors());
				isValidRecord = false;
			}
		}

		for (DataFieldDefinition fieldDefinition : repetitionCounter.keySet()) {
			if (repetitionCounter.get(fieldDefinition) > 1
				&& fieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
				validationErrors.add(new ValidationError(getId(), fieldDefinition.getTag(),
					ValidationErrorType.NonrepeatableField,
					String.format(
						"there are %d instances",
						repetitionCounter.get(fieldDefinition)
					),
					fieldDefinition.getDescriptionUrl()
				));
				errors.add(String.format(
					"%s is not repeatable, however there are %d instances (%s)",
					fieldDefinition.getTag(),
					repetitionCounter.get(fieldDefinition), fieldDefinition.getDescriptionUrl()));
				isValidRecord = false;
			}
		}

		return isValidRecord;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	public List<String> search(String path, String query) {
		List<String> results = new ArrayList<>();
		if (path.equals("001") || path.equals("003") || path.equals("005")) {
			searchControlField(path, query, results);
		} else if (path.startsWith("tag006")) {
			searchPositionalControlField(control006, path, query, results);
		} else if (path.startsWith("tag007")) {
			searchPositionalControlField(control007, path, query, results);
		} else if (path.startsWith("tag008")) {
			searchPositionalControlField(control008, path, query, results);
		} else {
			Matcher matcher = dataFieldPattern.matcher(path);
			if (matcher.matches()) {
				String tag = matcher.group(1);
				String subfieldCode = matcher.group(2);
				if (datafieldIndex.containsKey(tag)) {
					for (DataField field : datafieldIndex.get(tag)) {
						if (searchDatafield(query, results, subfieldCode, field)) break;
					}
				}
			}
			matcher = positionalPattern.matcher(path);
			if (matcher.matches()) {
				searchByPosition(query, results, matcher);
			}
		}
		return results;
	}

	public List<String> select(MarcSpec selector) {
		List<String> results = new ArrayList<>();
		if (controlfieldIndex.containsKey(selector.getFieldTag())) {
			for (MarcControlField field : controlfieldIndex.get(selector.getFieldTag())) {
				if (field == null)
					continue;
				if (simpleControlTags.contains(field.definition.getTag())) {
					results.add(field.getContent());
				} else {
					// TODO: check control subfields
					results.add(field.getContent());
				}
			}

		} else if (datafieldIndex.containsKey(selector.getFieldTag())) {
			for (DataField field : datafieldIndex.get(selector.getFieldTag())) {
				if (field == null)
					continue;
				for (String subfieldCode : selector.getSubfieldsAsList()) {
					List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
					if (subfields == null)
						continue;
					for (MarcSubfield subfield : subfields) {
						results.add(subfield.getValue());
					}
				}
			}
		}
		else if (selector.getFieldTag().equals("008")) {
			if (selector.getCharStart() != null) {
				ControlSubfieldDefinition definition = control008.getSubfieldByPosition(selector.getCharStart());
				results.add(control008.getMap().get(definition));
			} else {
				results.add(control008.getContent());
			}
		}
		return results;
	}

	private void searchByPosition(String query, List<String> results, Matcher matcher) {
		String tag = matcher.group(1);
		String position = matcher.group(2);
		int start, end;
		if (position.contains("-")) {
			String[] parts = position.split("-", 2);
			start = Integer.parseInt(parts[0]);
			end = Integer.parseInt(parts[1]);
		} else {
			start = Integer.parseInt(position);
			end = start + 1;
		}
		String content = null;
		if (tag.equals("Leader")) {
			content = leader.getLeaderString();
		} else {
			MarcControlField controlField = null;
			switch (tag) {
				case "006": controlField = control006; break;
				case "007": controlField = control007; break;
				case "008": controlField = control008; break;
			}
			if (controlField != null)
				content = controlField.getContent();
		}

		if (content != null && content.substring(start, end).equals(query)) {
			results.add(content.substring(start, end));
		}
	}

	private boolean searchDatafield(String query, List<String> results,
											  String subfieldCode, DataField field) {
		if (subfieldCode.equals("ind1") && field.getInd1().equals(query)) {
			results.add(field.getInd1());
			return true;
		} else if (subfieldCode.equals("ind2") && field.getInd2().equals(query)) {
			results.add(field.getInd2());
			return true;
		} else {
			List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
			if (subfields != null) {
				for (MarcSubfield subfield : subfields) {
					if (subfield.getValue().equals(query)) {
						results.add(subfield.getValue());
						return true;
					}
				}
			}
		}
		return false;
	}

	private void searchControlField(String path, String query, List<String> results) {
		MarcControlField controlField = null;
		switch (path) {
			case "001": controlField = control001; break;
			case "003": controlField = control003; break;
			case "005": controlField = control005; break;
		}
		if (controlField.getContent().equals(query))
			results.add(controlField.getContent());
	}

	private void searchPositionalControlField(MarcPositionalControlField controlField,
															String path, String query, List<String> results) {
		if (controlField != null) {
			Map<ControlSubfieldDefinition, String> map = controlField.getMap();
			for (ControlSubfieldDefinition subfield : controlField.getMap().keySet()) {
				if (subfield.getId().equals(path)) {
					if (map.get(subfield).equals(query))
						results.add(map.get(subfield));
					break;
				}
			}
		}
	}
}
