package ise.gameoflife.enviroment;

import ise.gameoflife.AgentDataModel;
import ise.gameoflife.PublicAgentDataModel;
import ise.gameoflife.models.Food;
import ise.gameoflife.models.Group;
import ise.gameoflife.tokens.RegistrationRequest;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;
import org.simpleframework.xml.ElementMap;
import presage.environment.AEnvDataModel;

/**
 *
 * @author Benedict Harcourt
 */
public class EnvironmentDataModel extends AEnvDataModel
{

	public final static long serialVersionUID = 1L;
	/**
	 * A sorted list/map of all the state of all players in the game
	 */
	@ElementMap(keyType = String.class, valueType = AgentDataModel.class)
	private TreeMap<String, PublicAgentDataModel> agents;
	/**
	 * List of all the available food types in the environment
	 */
	@ElementMap
	private HashMap<UUID, Food> availableFoodTypes;
	/**
	 * List of all the groups in the environment
	 */
	@ElementMap
	private HashMap<UUID, Group> agentGroups;

	/**
	 * Serialisable no-arg constructor, do not use
	 * @deprecated
	 */
	@Deprecated
	public EnvironmentDataModel()
	{
		super();
	}

	public Food getFoodById(UUID id)
	{
		return availableFoodTypes.get(id);
	}

	public Group getGroupById(UUID id)
	{
		return agentGroups.get(id);
	}

	public PublicAgentDataModel getAgentById(String id)
	{
		return agents.get(id);
	}

	public boolean removeParticipant(String id)
	{
		return (agents.remove(id)!=null);
	}

	public boolean registerParticipant(RegistrationRequest id)
	{
		agents.put(id.getParticipantID(), id.getModel());
		return true;
	}
}