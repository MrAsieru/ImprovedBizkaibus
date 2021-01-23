import ImprovedBizkaibus.BusStops.BusStopsList;
import ImprovedBizkaibus.Lines.LineList;
import ImprovedBizkaibus.LiveData.BusLiveData;
import ImprovedBizkaibus.LiveData.BusStopLiveData;
import ImprovedBizkaibus.LiveData.LineLiveData;
import ImprovedBizkaibus.Locations.ProvinceList;

public class Test {
	public static void main(String[] args) {
		ProvinceList.getInstance();
		LineList.getInstance();
		BusStopsList.getInstance();
		BusStopLiveData bs = BusStopsList.getInstance().getTownBusStops("48020").get(0).getLiveData();
		bs.updateAllLines();

		for(LineLiveData lld : bs.getLineList()){
			System.out.println("Line: "+lld.getLine().getId());
			System.out.println("Route: "+lld.getRoute());
			System.out.println("Buses:");
			for (BusLiveData bld : lld.getBuses()){
				if(bld != null){
					System.out.println("\tDestination: "+bld.getDestination());
					System.out.println("\tDistance: "+bld.getDistance()+" m");
					System.out.println("\tTime: "+bld.getTime()+" min");
				}
			}
		}
	}
}
