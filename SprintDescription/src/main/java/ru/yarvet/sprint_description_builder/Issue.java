package ru.yarvet.sprint_description_builder;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Issue {

	private String id;
	private String summary;
	private String description;
	private String subsystem;
	private String type;
	private HashMap<String, String> fields = new HashMap<String, String>();
	private boolean isLoad;
	
	public Issue(JSONObject json) {
		id =  json.getString("id");
	}
	
	public void loadAdditionalInfo(JSONObject json) {
		JSONArray fieldsJSON = json.getJSONArray("field");
		isLoad = true;
		for (Object field : fieldsJSON) {
			String fieldName = ((JSONObject) field).getString("name");
			String value = (((JSONObject) field).get("value")).toString();
			
			fields.put(fieldName, value);
			
			if(fieldName.equals("Итог")) {
				description = value;
			} else if(fieldName.equals("summary")){
				summary = value;
			}else if(fieldName.equals("Подсистема")){
				subsystem = value;
			}else if(fieldName.equals("Type")){
				type = value;	
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public String getSummary() {
		return summary == null ? null:  summary.replace("[\"", "").replace("\"]", "");
	}

	public String getField(String fieldName) {
		return fields.get(fieldName);
	}
	
	public String getDescription() {
		return description == null ? null : description.replace("[\"", "")
				.replace("\"]", "")
				.replace("\\\"", "\"")
				.replace("\\n", "<br>");
	}
	
	public String getType() {
		return type;
	}

	public boolean isLoad() {
		return isLoad;
	}
	
	public String getSubsystem() {
		return subsystem == null ? "": subsystem.replace("[\"", "").replace("\"]", "");
	}


}
