import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import java.util.Date;

public class GetInfoLinea {
	static String uri1 = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/GetHorario"
			+ "?sCLINEA=@Li"
			+ "&sFECHAHORARIO=@FH";
	static String uri2 = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/gfReturn_SPTQCCABB"
			+ "?intTipoConsulta=1"
			+ "&strCodigoLinea=@cl"
			+ "&strFecha=";
	static String uri3 = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/GetLineas_JSON"
			+ "?callback="
			+ "&intTipoConsulta=@tc"
			+ "&strCodigoLinea=@cl"
			+ "&strNumeroRuta="
			+ "&strSentido="
			+ "&strDescripcionLinea="
			+ "&strListaCodigosLineas=";
	static String uri4 = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/gfReturn_Buscar_ItinerariosLinea"
			+ "?strCODLINEA=@cl"
			+ "&strNUMRUTA=@nr"
			+ "&strSENTIDO=@se";
	static String uri5 = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/gfReturn_Consultar_TBTQ0001_PARADAS_BIZKAIBUS"
			+ "?intTipoConsulta=@tc"
			+ "&strCodigoProvinvia_Parada=@cpr"
			+ "&strCodigoMunicipio_Parada=@cm"
			+ "&strCodigoParada_Parada=@cpa";
	static ArrayList<String> Incidencia = new ArrayList<>();
	static LinkedHashMap<String, ArrayList<String>> Itinerario = new LinkedHashMap<>();
	public GetInfoLinea() {		
	}
	
	public static String getHorarioIC(String strLinea, String strFecha){
		String result = new String();
		String url = uri1;
		url = url.replace("@Li", strLinea);
		url = url.replace("@FH", strFecha);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");	
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			result = element.getAttribute("HT_TEXTOIC");				
		}
		return result;
	}
	
	public static String getHorarioVC(String strLinea, String strFecha){
		String result = new String();
		String url = uri1;
		url = url.replace("@Li", strLinea);
		url = url.replace("@FH", strFecha);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");	
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			result = element.getAttribute("HT_TEXTOVC");				
		}
		return result;
	}
	
	public static String getHorarioIE(String strLinea, String strFecha){
		String result = new String();
		String url = uri1;
		url = url.replace("@Li", strLinea);
		url = url.replace("@FH", strFecha);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");	
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			result = element.getAttribute("HT_TEXTOIE");				
		}
		return result;
	}
	
	public static String getHorarioVE(String strLinea, String strFecha){
		String result = new String();
		String url = uri1;
		url = url.replace("@Li", strLinea);
		url = url.replace("@FH", strFecha);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");	
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			result = element.getAttribute("HT_TEXTOVE");				
		}
		return result;
	}
	
	public static long caducidadHorarioUnix (String strLinea) {
		long epoch = 0;
		String url = uri2;
		url = url.replace("@cl", strLinea);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			String fecha = element.getAttribute("FechaTramoCalendarioSiguiente");			
			try {
				epoch = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fecha+" 00:00:00").getTime() / 1000;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return epoch;
	}
	
	public static ArrayList<String> loadIncidencia(String strLinea) {
		if(!Incidencia.isEmpty()) Incidencia.clear();
		String url = uri3;
		url = url.replace("@tc", "1");
		url = url.replace("@cl", strLinea);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			Incidencia.add(element.getAttribute("IR_CLINEA"));
			Incidencia.add(element.getAttribute("RL_DENOMI"));
			Incidencia.add(element.getAttribute("LN_INCCAS"));
			Incidencia.add(element.getAttribute("LN_INCEUS"));		
		}
		return Incidencia;
	}
	
	public static LinkedHashMap<String, ArrayList<String>> getItinerario (String strLinea, String strSentido){
		Itinerario.clear();
		String url = uri4;
		url = url.replace("@cl", strLinea);
		url = url.replace("@nr", "001");
		url = url.replace("@se", strSentido);
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			ArrayList<String> aList = new ArrayList<>();
			aList.add(element.getAttribute("PR_DENOMI"));
			aList.add(element.getAttribute("DescripcionMunicipio"));			
			
			String url2 = uri5;
			url2 = url2.replace("@tc", "3");
			url2 = url2.replace("@cpr", element.getAttribute("IR_PROVIN"));
			url2 = url2.replace("@cm", element.getAttribute("IR_MUNICI"));
			url2 = url2.replace("@cpa", element.getAttribute("IR_PARADA"));
			Document doc2 = FromXml.getDoc(url2);
			NodeList nodeList2 = doc2.getElementsByTagName("Registro");
			for(int e = 0; e<nodeList2.getLength(); e++) {
				org.w3c.dom.Node node2 = nodeList2.item(e);
				Element element2 = (Element) node2;
				UTM2Deg conv = new UTM2Deg();
				Double[] dbl = conv.UTM2DegConv(element2.getAttribute("COORDENADA_X")+" "+element2.getAttribute("COORDENADA_Y"));
				aList.add(dbl[0].toString());
				aList.add(dbl[1].toString());
			}			
			Itinerario.put(element.getAttribute("IR_PROVIN")+element.getAttribute("IR_MUNICI")+element.getAttribute("IR_PARADA"), aList);
		}
		return Itinerario;
	}
	
	public static String getPDF (String strLinea, String strSentido) {
		return "http://apli.bizkaia.net/APPS/DANOK/TQ/DATOS_PARADAS/ITINERARIOS/"+strLinea+strSentido+".PDF";
	}
	
	private static class UTM2Deg
	{
	    double latitude;
	    double longitude;
	    private Double[] UTM2DegConv(String UTM)
	    {
	    	Double[] dblReturn = new Double[2];
	    	UTM = "30 N "+UTM;
	    	String[] parts=UTM.split(" ");
	        int Zone=Integer.parseInt(parts[0]);
	        char Letter=parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
	        double Easting=Double.parseDouble(parts[2]);
	        double Northing=Double.parseDouble(parts[3]);           
	        double Hem;
	        if (Letter>'M')
	            Hem='N';
	        else
	            Hem='S';            
	        double north;
	        if (Hem == 'S')
	            north = Northing - 10000000;
	        else
	            north = Northing;
	        latitude = (north/6366197.724/0.9996+(1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)-0.006739496742*Math.sin(north/6366197.724/0.9996)*Math.cos(north/6366197.724/0.9996)*(Math.atan(Math.cos(Math.atan(( Math.exp((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*( 1 -  0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996 )/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996 - 0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996 )*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996)*3/2)*(Math.atan(Math.cos(Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996))*180/Math.PI;
	        latitude=Math.round(latitude*10000000);
	        latitude=latitude/10000000;
	        longitude =Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*( north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2* north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3)) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))*180/Math.PI+Zone*6-183;
	        longitude=Math.round(longitude*10000000);
	        longitude=longitude/10000000;
	        dblReturn[0] = latitude;
	        dblReturn[1] = longitude;
			return dblReturn;       
	    }   
	}
}
