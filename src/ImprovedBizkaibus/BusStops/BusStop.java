package ImprovedBizkaibus.BusStops;

import ImprovedBizkaibus.LiveData.BusStopLiveData;
import ImprovedBizkaibus.Locations.Town;

public class BusStop {
	private Town town;
	private float lat_x;
	private float lat_y;
	private String id;
	private String name;
	private BusStopLiveData liveData;

	public BusStop(Town pTown, float pLat_x, float pLat_y, String pId, String pName){
		this.town = pTown;
		this.lat_x = pLat_x;
		this.lat_y = pLat_y;
		this.id = pId;
		this.name = pName;
		this.liveData = new BusStopLiveData(this);
	}

	public Town getTown() {
		return town;
	}

	public float getLat_x() {
		return lat_x;
	}

	public float getLat_y() {
		return lat_y;
	}

	public String getRelativeId() {
		return id;
	}

	public String getFullId(){
		return town.getFullId()+id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public BusStopLiveData getLiveData() {
		return liveData;
	}
}
