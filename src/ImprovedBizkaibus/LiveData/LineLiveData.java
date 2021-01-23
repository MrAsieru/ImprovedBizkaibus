package ImprovedBizkaibus.LiveData;

import ImprovedBizkaibus.BusStops.BusStop;
import ImprovedBizkaibus.Lines.Line;
import java.util.HashMap;
import java.util.Map;

public class LineLiveData {
	private Line line;
	private BusStop busStop;
	private String route;
	private BusLiveData[] buses;

	public LineLiveData(Line pLine, BusStop pBusStop, String pRoute){
		this.line = pLine;
		this.busStop = pBusStop;
		this.route = pRoute;
		buses = new BusLiveData[2];
	}

	public void updateBuses(BusLiveData pBus1, BusLiveData pBus2){
		buses[0] = pBus1;
		buses[1] = pBus2;
	}

	public Line getLine() {
		return line;
	}

	public BusStop getBusStop() {
		return busStop;
	}

	public String getRoute() {
		return route;
	}

	public BusLiveData[] getBuses() {
		return buses.clone();
	}
}
