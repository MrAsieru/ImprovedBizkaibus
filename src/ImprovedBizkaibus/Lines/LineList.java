package ImprovedBizkaibus.Lines;

import ImprovedBizkaibus.Constants;
import ImprovedBizkaibus.Helper.Helper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LineList {
	private Map<String,Line> list;
	private static LineList instance;

	private LineList(){
		this.list = new HashMap<>();
		load();
	}

	public static LineList getInstance(){
		if(instance == null) instance = new LineList();
		return instance;
	}

	private void load(){
		Map<String,String> params = new LinkedHashMap<>();
		params.put("callback","");
		params.put("iTipoConsulta","1");
		params.put("sCodigoLinea","");
		params.put("sNumeroRuta","");
		params.put("sSentido","");
		params.put("SDescripcionLinea","");
		params.put("sListaCodigosLineas","");
		for(Object obj : Helper.getJSON(Constants.GET_LINEAS,params).getJSONObject("Consulta").getJSONArray("Lineas")){
			JSONObject line = (JSONObject) obj;
			list.put(line.getString("IR_CLINEA"),
					new Line(line.getString("IR_CLINEA"),
							line.getString("RL_DENOMI"),
							line.getString("LN_INCCAS"),
							line.getString("LN_INCEUS"),
							(line.getString("VVMONTESHIERRO").equals(""))?true:false));
		}
	}

	public Line getLine(String pName){
		return list.get(pName);
	}
}
