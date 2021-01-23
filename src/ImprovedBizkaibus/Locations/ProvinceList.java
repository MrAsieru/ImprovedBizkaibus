package ImprovedBizkaibus.Locations;

import ImprovedBizkaibus.Constants;
import ImprovedBizkaibus.Helper.Helper;
import org.json.JSONObject;

import java.util.HashMap;

public class ProvinceList {
	private HashMap<String,Province> list;
	private static ProvinceList instance;

	private ProvinceList(){
		this.list = new HashMap<>();
		load();
	}

	public static ProvinceList getInstance(){
		if (instance == null) instance = new ProvinceList();
		return instance;
	}

	/***
	 * Get province from list
	 * @param pId Province id: 01-Alava, 20-Gipuzkoa, 39-Cantabria, 48-Bizkaia
	 * @return a province if the id exist, null if not.
	 */
	public Province getProvince(String pId){
		return list.get(pId);
	}

	private void load(){
		for(Object obj : Helper.XMLtoJSON(Constants.GET_MUNICIPIOS,new HashMap<>()).getJSONObject("Consulta").getJSONArray("Registro")){
			JSONObject town = (JSONObject) obj;
			Province pr = null;
			if(!list.containsKey(String.format("%02d",town.getInt("CodigoProvincia")))) {
				pr = new Province(String.format("%02d",town.getInt("CodigoProvincia")),town.getString("DescripcionProvincia"));
				list.put(String.format("%02d",town.getInt("CodigoProvincia")),pr);
			}
			if(pr == null) pr = list.get(String.format("%02d",town.getInt("CodigoProvincia")));
			pr.addTown(new Town(pr,String.format("%03d",town.getInt("CodigoMunicipio")),town.getString("DescripcionMunicipio")));
		}
	}
}
