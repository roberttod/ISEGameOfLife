package ise.gameoflife.inputs;

/**
 *
 * @author Olly
 */
public final class HuntResult extends GenericInput
{
	
	private String agent;
	private double nutrition;
	
	/**
	 * Creates a new instance of the HuntResult class, which gives the result of a 
	 * Hunt action.
	 * @param nutrition The amount of food gained in this hunt
	 * @param time The simulation time at which this result occurred 
	 */
	public HuntResult(String agent, double nutrition, long time){
		super(time, "huntresult:" + nutrition);
		this.nutrition = nutrition;
		this.agent = agent;
	}

	/**
	 * Returns nutrition value of food hunted
	 * @return The nutrition value of food hunted
	 */
	public double getNutritionValue(){
		return nutrition;
	}

	/**
	 * Gets the agent that returned this much food
	 */
	public String getAgent()
	{
		return agent;
	}

}
