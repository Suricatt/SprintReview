package ru.yarvet.sprint_description_builder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import ru.yarvet.sprint_description_builder.SprintDescriptionBuilder.ListSubsystemIssues;

public class DescriptionBuilder {

	String sprint;
	
	public String getDescription(ListSubsystemIssues issuesCollection) throws IOException {
		StringWriter writer = new StringWriter();
		
		VelocityContext context = new VelocityContext();
        context.put("sprintSubsystems", issuesCollection);
        context.put("issuesHeader", "Выполненные доработки");
        context.put("errorsHeader", "Исправленные ошибки");
        context.put("null", null);
       
        Properties p = new Properties();
        p.put("input.encoding", "UTF-8");
        p.put("output.encoding", "UTF-8");
        VelocityEngine engine = new VelocityEngine();
        engine.init(p);
        
        Template template = engine.getTemplate("/templates/maket.vm");
        template.merge(context, writer);
        return writer.toString();
	}
	
}
