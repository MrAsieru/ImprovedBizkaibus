package ImprovedBizkaibus.BusStops;

import ImprovedBizkaibus.Constants;
import ImprovedBizkaibus.Locations.Province;
import ImprovedBizkaibus.Locations.ProvinceList;
import ImprovedBizkaibus.Locations.Town;
import ImprovedBizkaibus.Helper.Helper;
import org.json.JSONObject;

import java.util.*;

public class BusStopsList {
	private static BusStopsList instance;
	private Map<String, List<BusStop>> listByProvince; //xx where xx: Province ID
	private Map<String, List<BusStop>> listByTown; //xxyyy where xx: Province ID, yyy: TownID
	private Map<String, BusStop> list; //xxyyyzzz where xx: Province ID, yyy: TownID, zzz: Stop ID


	private BusStopsList(){
		this.listByProvince = new HashMap<>();
		this.listByTown = new HashMap<>();
		this.list = new HashMap<>();
		load();
	}

	public static BusStopsList getInstance(){
		if (instance == null) instance = new BusStopsList();
		return instance;
	}

	private void load(){
		Map<String,String> params = new LinkedHashMap<>();
		params.put("intTipoConsulta","2");
		params.put("strCodigoProvinvia_Parada","");
		params.put("strCodigoMunicipio_Parada","");
		params.put("strCodigoParada_Parada","");
		for(Object obj : Helper.XMLtoJSON(Constants.GET_PARADAS,params).getJSONObject("Consulta").getJSONArray("Registro")){
			JSONObject busStop = (JSONObject) obj;
			Province pr = ProvinceList.getInstance().getProvince(busStop.getString("PROVINCIA"));
			if(!listByProvince.containsKey(pr.getId())) listByProvince.put(pr.getId(),new LinkedList<>());
			Town tw = pr.getTownById(busStop.getString("MUNICIPIO"));
			if(!listByTown.containsKey(tw.getFullId())) listByTown.put(tw.getFullId(),new LinkedList<>());
			float[] coord = Helper.UTM2DegConv(busStop.getString("COORDENADA_X")+" "+busStop.getString("COORDENADA_X"));
			BusStop bs = new BusStop(tw,coord[0],coord[1],busStop.getString("PARADA"),busStop.getString("DENOMINACION"));
			listByProvince.get(bs.getTown().getProvince().getId()).add(bs);
			listByTown.get(bs.getTown().getFullId()).add(bs);
			list.put(bs.getFullId(),bs);
		}
	}

	public BusStop getBusStop(String pId){
		return list.get(pId);
	}

	public List<BusStop> getProvinceBusStops(String pProvinceId){
		return listByProvince.get(pProvinceId);
	}

	public List<BusStop> getTownBusStops(String pTownId){
		return listByTown.get(pTownId);
	}
}
