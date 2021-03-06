package ise.gameoflife.simulatons;

import ise.gameoflife.agents.TestAgent;
import ise.gameoflife.agents.TestGroupableAgent;
import ise.gameoflife.enviroment.EnvironmentDataModel;
import ise.gameoflife.groups.TestGroup;
import ise.gameoflife.models.Food;
import ise.gameoflife.participants.AbstractAgent;
import ise.gameoflife.participants.AbstractGroupAgent;
import ise.gameoflife.plugins.ErrorLog;
import ise.gameoflife.plugins.HuntersAlivePlugin;
import ise.gameoflife.plugins.DatabasePlugin;
import ise.gameoflife.plugins.HunterListPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import presage.EventScriptManager;
import presage.Participant;
import presage.PluginManager;
import presage.PresageConfig;
import presage.ScriptedEvent;
import presage.events.CoreEvents.ActivateParticipant;
import presage.Environment;
import presage.configure.ConfigurationWriter;

/**
 *
 * @author Benedict Harcourt
 */
public class AgentsAndGroup
{

	public static void main(String args[])
	{
		PresageConfig presageConfig = new PresageConfig();

		// Basic config
		presageConfig.setComment("Simple Test of 10 agents consuming food until death");
		presageConfig.setIterations(100);
		presageConfig.setRandomSeed(0);

		presageConfig.setOutputFolder(new File(System.getProperty("user.dir"), "output").getAbsolutePath());
		presageConfig.setThreadDelay(1);
		presageConfig.setAutorun(false);

		// Path configuarations
		String configPath = new File(System.getProperty("user.dir"),"simulations/basicgrouping").getAbsolutePath();
		presageConfig.setPluginsConfigPath(configPath + "/plugins.xml");
		presageConfig.setEventscriptConfigPath(configPath + "/methods.xml");
		presageConfig.setParticipantsConfigPath(configPath + "/participants.xml");
		presageConfig.setEnvironmentConfigPath(configPath + "/environment.xml");

		// All the big objects
		PluginManager pm = new PluginManager();
		pm.addPlugin(new HuntersAlivePlugin(configPath + "/population.png", 1500, 1200));
		pm.addPlugin(new ErrorLog());
		pm.addPlugin(new DatabasePlugin(configPath + "/simulation.db"));
		pm.addPlugin(new HunterListPlugin());

		TreeMap<String, Participant> parts = new TreeMap<String, Participant>();

		EventScriptManager ms = new EventScriptManager();

		AbstractAgent a;
		
		for (int i = 0; i < 10; i++)
		{
			a = new TestAgent(20, 2);
			parts.put(a.getId(), a);
			ms.addPreEvent(new ScriptedEvent(-1, new ActivateParticipant(a.getId())));
		}
		for (int i = 0; i < 10; i++)
		{
			a = new TestGroupableAgent(20, 2);
			parts.put(a.getId(), a);
			ms.addPreEvent(new ScriptedEvent(-1, new ActivateParticipant(a.getId())));
		}

		HashMap<String, Food> foods = new HashMap<String, Food>();
		Food rabbit = new Food("rabbit", 1, 1);
		foods.put(rabbit.getId().toString(), rabbit);
		Food stag = new Food("stag", 5, 2);
		foods.put(stag.getId().toString(), stag);

		List<Class<? extends AbstractGroupAgent>> groups = new ArrayList<Class<? extends AbstractGroupAgent>>();
		groups.add(TestGroup.class);
		
		EnvironmentDataModel dm = new EnvironmentDataModel("Single Certain Death", foods, groups);
		Environment environment = (Environment)new ise.gameoflife.enviroment.Environment(true, 0, dm);

		presageConfig.setEnvironmentClass(environment.getClass());
		ConfigurationWriter.write(configPath + "/sim.xml", presageConfig, parts, environment, pm, ms);
	}

	private AgentsAndGroup()
	{
		// Nothing to see here. Move along, citizen!
	}

}
