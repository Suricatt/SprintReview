package ru.yarvet.sprint_description_builder;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.Test;

public class SprintTest {

	@Test
	public void testSprint_emptyFinish() {
		
		JSONObject request = new JSONObject();
		request.put("id", "test");
		request.put("name", "test");
		request.put("finish", JSONObject.NULL);
		
		Sprint sprint = new Sprint(request);
		assertEquals(sprint.getId(), "test");
		assertEquals(sprint.getFinish(), new Date());
		
	}
	
	@Test
	public void testSprint() throws ParseException {
		
		JSONObject request = new JSONObject();
		request.put("id", "test");
		request.put("name", "test");
		request.put("finish", "1558267200000");
		
		Sprint sprint = new Sprint(request);
		assertEquals(sprint.getId(), "test");
		String date="19/05/2019";  
		assertEquals(sprint.getFinish(), new SimpleDateFormat("dd/MM/yyyy").parse(date));
		
	}

}
