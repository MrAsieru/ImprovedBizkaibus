package ImprovedBizkaibus.Locations;

import java.util.HashMap;

public class Province {
	private String id;
	private String name;
	private HashMap<String,Town> listByName;
	private HashMap<String,Town> listById;

	public Province(String pId, String pName){
		this.id = pId;
		this.name = pName;
		this.listByName = new HashMap<>();
		this.listById = new HashMap<>();
	}

	public void addTown(Town pTown){
		listById.put(pTown.getRelativeId(),pTown);
		listByName.put(pTown.getName(),pTown);
	}

	/**
	 * Get the province ID
	 * @return the province ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the province name
	 * @return the province name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get a town with the name
	 * @param pName The town name
	 * @return the town
	 */
	public Town getTownByName(String pName){
		return listByName.get(pName);
	}

	/**
	 * Get a town with the ID
	 * @param pId The town ID
	 * @return the town
	 */
	public Town getTownById(String pId){
		return listById.get(pId);
	}
}
