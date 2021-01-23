package ImprovedBizkaibus.LiveData;

public class BusLiveData {
	private LineLiveData lineLiveData;
	private String destination;
	private String distance;
	private int time;

	public BusLiveData(LineLiveData lineLiveData, String destination, String distance, int time) {
		this.lineLiveData = lineLiveData;
		this.destination = destination;
		this.distance = distance;
		this.time = time;
	}

	public LineLiveData getLineLiveData() {
		return lineLiveData;
	}

	public String getDestination() {
		return destination;
	}

	public String getDistance() {
		return distance;
	}

	public int getTime() {
		return time;
	}
}
