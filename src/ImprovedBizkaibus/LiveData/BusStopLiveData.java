package ImprovedBizkaibus.LiveData;

import ImprovedBizkaibus.BusStops.BusStop;
import ImprovedBizkaibus.Constants;
import ImprovedBizkaibus.Lines.LineList;
import ImprovedBizkaibus.Helper.Helper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class BusStopLiveData {
	private BusStop busStop;
	private Map<String, LineLiveData> lines; //Key: Line, Value: LineLiveData

	public BusStopLiveData(BusStop pBusStop){
		this.busStop = pBusStop;
		this.lines = new TreeMap<>();
	}

	public void updateAllLines(){
		lines.clear();
		Map<String,String> params = new TreeMap<>();
		params.put("strLinea","");
		params.put("strParada",busStop.getFullId());
		Object linesData = Helper.XMLtoJSON(Constants.GET_PASO_PARADA,params).get("GetPasoParadaResult");

		if (!(linesData instanceof String)){ //At least one line
			if (linesData instanceof JSONObject) { //Only one line
				updateLine(((JSONObject) linesData).getJSONObject("PasoParada"));
			} else if (linesData instanceof JSONArray){ //More than one line
				for(Object obj : ((JSONObject) linesData).getJSONArray("PasoParada")){
					updateLine((JSONObject) obj);
				}
			}
		}
	}

	private void updateLine(JSONObject lineData){
		LineLiveData lld;
		lld = (lines.containsKey(lineData.getString("linea"))?
				lines.get(lineData.getString("linea")):
				new LineLiveData(
						LineList.getInstance().getLine(lineData.getString("linea")),
						busStop,
						lineData.getString("ruta")));
		lld.updateBuses(
				new BusLiveData(lld,
						lineData.getJSONObject("e1").getString("destino"),
						lineData.getJSONObject("e1").getString("metros"),
						Integer.parseInt(lineData.getJSONObject("e1").getString("minutos"))),
				(!lineData.getJSONObject("e2").getString("destino").equals(""))?
					new BusLiveData(lld,
							lineData.getJSONObject("e2").getString("destino"),
							lineData.getJSONObject("e2").getString("metros"),
							Integer.parseInt(lineData.getJSONObject("e2").getString("minutos"))):
					null
		);
		lines.put(lld.getLine().getId(),lld);
	}

	public LineLiveData getLine(String pName){
		return lines.get(pName);
	}

	public List<LineLiveData> getLineList(){
		return new ArrayList<>(lines.values());
	}
}
