package ru.yarvet.sprint_description_builder;

import static org.junit.Assert.*;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class IssueTest {

	@Test
	public void issueFromJSON() {
		JSONObject test = new JSONObject();
		test.put("id", "test");
		
		Issue issue = new Issue(test);
		assertEquals("test", issue.getId());
		assertEquals(false, issue.isLoad());
		
	}
	
	@Test
	public void loadAdditionInfo() {
		
		JSONObject test = new JSONObject();
		test.put("id", "test");
		
		JSONArray fields = new JSONArray();
		fields.put(getField("Итог", "Тестовый итог"));
		fields.put(getField("summary", "summary test"));
		
		JSONObject test2 = new JSONObject();
		test2.put("field", fields);
		Issue issue = new Issue(test);
		issue.loadAdditionalInfo(test2);
		
		assertEquals("Тестовый итог", issue.getDescription());
		
	}
	
	@Test
	public void loadAdditionInfoWhithoutInfo() {
		
		JSONObject test = new JSONObject();
		test.put("id", "test");
		
		JSONArray fields = new JSONArray();
		fields.put(getField("summary", "summary test"));
		
		JSONObject test2 = new JSONObject();
		test2.put("field", fields);
		
		Issue issue = new Issue(test);
		issue.loadAdditionalInfo(test2);
		
		assertEquals(null, issue.getDescription());
		
	}
	
	private JSONObject getField(String name, String value) {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("value", value);
		return obj;
	}

}
