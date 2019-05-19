package ru.yarvet.sprint_description_builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

	public Sprint getSprintFromResponse(String response, String sprintDate) throws ParseException {
		Sprint mySprint = null;
		
		JSONArray sprintsSJON = new JSONArray(response);
		LinkedList<Sprint> sprints = new LinkedList<Sprint>();
		for(Object sprintJSON : sprintsSJON) {
			sprints.addFirst(new Sprint((JSONObject) sprintJSON));
		}
		ListIterator<Sprint> interator = sprints.listIterator(); 
		Date myDate;
		myDate = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(sprintDate);
		while (interator.hasNext()) {
			Sprint sprint = (Sprint) interator.next();
			if(sprint.getFinish().equals(myDate)) {
				mySprint = sprint;
				break;
			}
		}
		return mySprint;
	}
	
	public ArrayList<Issue> getIssuesFromResponse(String response) throws ParseException{
		ArrayList<Issue> issues = new ArrayList<Issue>();
		JSONArray issuesSJON = new JSONArray(response);
		for(Object issueJSON : issuesSJON) {
			issues.add(new Issue((JSONObject)issueJSON));
		}
		return issues;
	}
	
	public JSONObject getIssueAdditionalInfoFromResponse(String response) {
		return new JSONObject(response);
	}	
	
}
