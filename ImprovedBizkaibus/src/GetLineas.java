import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GetLineas {
	static String uri = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/GetLineas_JSON"
			+ "?callback="
			+ "&intTipoConsulta=@tc"
			+ "&strCodigoLinea="
			+ "&strNumeroRuta="
			+ "&strSentido="
			+ "&strDescripcionLinea="
			+ "&strListaCodigosLineas=";	
	static LinkedHashMap<String, ArrayList<String>> info = new LinkedHashMap<>();
	
	
	public GetLineas(){		
	}
	/*
	 * Documento ejemplo:
	 * <Consulta>
	 * 		<Registro 
	 * 			IR_CLINEA="NUMERO DE LINEA ej. A0651"
	 * 			RL_DENOMI="NOMBRE DE LINEA ej. Bilbao-Balmaseda"
	 * 			LN_INCCAS="INCIDENCIA EN CASTELLANO"
	 * 			LN_INCEUS="INCIDENCIA EN EUSKERA"
	 * 		/>
	 * </Consulta>
	 * 
	 */ 
	
	public static void load(){
		info.clear();
		String url = uri;
		url = url.replace("@tc", "1");
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");	
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			ArrayList<String>aList = new ArrayList<>();
			aList.add(element.getAttribute("RL_DENOMI"));
			aList.add(element.getAttribute("LN_INCCAS"));
			aList.add(element.getAttribute("LN_INCEUS"));
			info.put(element.getAttribute("IR_CLINEA"), aList);			
		}
	}
	
	public static ArrayList<String>getLineas(){
		ArrayList<String>aList = new ArrayList<>();
		for(int i = 0; i<info.size(); i++) {
			aList.add(info.keySet().toArray()[i].toString());
		}		
		return aList;
	}
	
	public static String getNombre(String strLinea){
		if(info.containsKey(strLinea)) {
			return info.get(strLinea).get(0);
		}
		return null;		
	}
	
	public static String getIncCas(String strLinea) {		
		return info.get(strLinea).get(1);
	}
	
	public static String getIncEus(String strLinea) {		
		return info.get(strLinea).get(2);
	}
}
