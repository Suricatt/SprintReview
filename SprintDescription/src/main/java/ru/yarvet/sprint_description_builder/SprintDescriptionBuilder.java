package ru.yarvet.sprint_description_builder;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class SprintDescriptionBuilder {

	@Parameter(names = { "--date", "-d" }, required = true)
	String dateOfSprint;
	@Parameter(names = { "--out", "-o" }, required = true)
	String outputFile;

	static private final String API_URL = "https://yo.yarvet.ru";
	static private final String key = "perm:YWRtaW4=.RE8=.QtMesdk8XAsnNguTu7DcGnrWJxYF1t";
	static private final String agileId = "101-2";

	public static void main(String args[]) throws Exception {

		SprintDescriptionBuilder main = new SprintDescriptionBuilder();
		JCommander.newBuilder().addObject(main).build().parse(args);

		YouTrackClient client = new YouTrackClient(API_URL, key, agileId);
		Parser parser = new Parser();

		Sprint sprint = parser.getSprintFromResponse(client.getSprint(), main.dateOfSprint);

		ArrayList<Issue> issues = parser.getIssuesFromResponse(client.getIssueList(sprint));
		ListSubsystemIssues subsystemIssues = new ListSubsystemIssues();
		for (Issue issue : issues) {
			if (!issue.isLoad()) {
				JSONObject additionalIssueInfo = parser
						.getIssueAdditionalInfoFromResponse(client.getIssue(issue.getId()));
				issue.loadAdditionalInfo(additionalIssueInfo);
				subsystemIssues.instertIssue(issue);
			}
		}

		DescriptionBuilder descriptionBuilder = new DescriptionBuilder();
		FileWriter nFile = new FileWriter(main.outputFile);
		nFile.write(descriptionBuilder.getDescription(subsystemIssues));
		nFile.close();
	}

	static public class ListSubsystemIssues {

		private HashMap<String, ArrayList<Issue>> subsystemIssues;
		private HashMap<String, ArrayList<Issue>> subsystemIError;

		public ListSubsystemIssues() {
			subsystemIssues = new HashMap<String, ArrayList<Issue>>();
			subsystemIError = new HashMap<String, ArrayList<Issue>>();
		}

		private HashMap<String, String> subsystems = new HashMap<String, String>();

		public ArrayList<Issue> getError(String subsystem) {
			return subsystemIError.get(subsystem);
		}

		public ArrayList<Issue> getIssue(String subsystem) {
			return subsystemIssues.get(subsystem);
		}

		public HashMap<String, String> getSubsystems() {
			return subsystems;
		}

		private void instertIssue(Issue issue) {

			String type = issue.getType();
			HashMap<String, ArrayList<Issue>> map = null;
			if (type.equals("[\"Ошибка\"]")) {
				map = subsystemIError;
			} else if (type.equals("[\"Задание\"]")) {
				map = subsystemIssues;
			}

			if (map != null) {
				String subsystem = issue.getSubsystem();
				subsystems.put(subsystem, subsystem);
				if (!subsystemIError.containsKey(subsystem))
					subsystemIError.put(subsystem, new ArrayList<Issue>());
				if (!subsystemIssues.containsKey(subsystem))
					subsystemIssues.put(subsystem, new ArrayList<Issue>());

				map.get(subsystem).add(issue);

			}

		}

	}

}
