package ru.yarvet.sprint_description_builder;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class Sprint {
	
	private String id;
	private Date finish;
	private String name;
	
	public Sprint(JSONObject json) {
		id = (String) json.get("id");
		name = (String) json.get("name");
		if (json.get("finish")  == (JSONObject.NULL)) {
			finish = new Date();
		} else {
			Long sFinish = json.getLong("finish");
			finish = getStartOfDayFromLong(sFinish);
		}
	}

	private Date getStartOfDayFromLong(Long sFinish) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(sFinish));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public String getId() {
		return id;
	}
	public Date getFinish() {
		return finish;
	}
	public String getName() {
		return name;
	}
	

}
