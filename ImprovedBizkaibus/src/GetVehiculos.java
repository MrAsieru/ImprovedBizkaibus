import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 * 
 * Solamente llamar a los metodos
 * @author MrAsieru
 *
 */
public class GetVehiculos {
	private static String uri = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/GetVehiculos?strLinea="; 
	public GetVehiculos() {
	}
	/*
	 * 	Documento ejemplo:
	 * 		<Consulta>
	 * 			<InfoVehiculo>
     *  				<vehiculo>5027</vehiculo> GetVehiculos.getBuses(); -> String array
     *  				<linea>A0651</linea>
     *  				<sublinea>738</sublinea>
     *  				<coche></coche>
     *  				<serv_bus>J065152</serv_bus>
     *  				<conductor></conductor>
     *  				<serv_cond></serv_cond>
     *  				<estado></estado>
     *  				<estadoLocReal></estadoLocReal>
     *  				<xcoord>494279.000005259</xcoord>
     *  				<ycoord>4783068.99083602</ycoord>
     *			</InfoVehiculo>	
     *			<InfoVehiculo>
     *  				<vehiculo>5023</vehiculo> GetVehiculos.getBuses(); -> String array
     *  				<linea>A0651</linea>
     *  				<sublinea>737</sublinea>
     *  				<coche></coche>
     *  				<serv_bus>J065151</serv_bus>
     *  				<conductor></conductor>
     *  				<serv_cond></serv_cond>
     *  				<estado></estado>
     *  				<estadoLocReal></estadoLocReal>
     *  				<xcoord>490526.000012663</xcoord>
     *  				<ycoord>4784191.99083564</ycoord>
     *			</InfoVehiculo>
     *		</Consulta>
     *
     *Ejemplo: 
     *	GetVehiculos.getBuses() --> String[] {5027, 5023}
     *	GetVehiculos.getCoord() --> Double[] {Lat, Lon} -> Convertidas de UTM ETRS89 en zona 30 N a GPS(Lat, Lon)
	 */
	
	/**
	 * 
	 * @param strLinea Linea de bizkaibus (ej.A0651)
	 * @return ArrayList<String> Array autobuses (ej.5027, 5028)
	 */
	public static ArrayList<String> getBuses(String strLinea) {
		Document doc = FromXml.getDoc(uri+strLinea);
		ArrayList<String> strReturn = new ArrayList<>();
		NodeList nodeList = doc.getElementsByTagName("InfoVehiculo");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			strReturn.add(element.getElementsByTagName("vehiculo").item(0).getTextContent());			
		}
		return strReturn;
	}
	
	/**
	 * 
	 * @param strLinea Linea de bizkaibus (ej.A0651)
	 * @param strCodigoVehiculo Numero de autobús (ej.5027)
	 * @return Double[] Coordenadas GPS (Lat, Lon) (ej.43.261 -2.949)
	 */
	public static Double[] getCoord(String strLinea, String strCodigoVehiculo) {
		Document doc = FromXml.getDoc(uri+strLinea);
		Double[] dblReturn = new Double[2];
		NodeList nodeList = doc.getElementsByTagName("InfoVehiculo");
		for(int i = 0; i<nodeList.getLength(); i++) {			
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			if(element.getElementsByTagName("vehiculo").item(0).getTextContent().equals(strCodigoVehiculo)) {
				UTM2Deg conv = new UTM2Deg();
				dblReturn = conv.UTM2DegConv(element.getElementsByTagName("xcoord").item(0).getTextContent()+" "+element.getElementsByTagName("ycoord").item(0).getTextContent());
				System.out.println(element.getElementsByTagName("xcoord").item(0).getTextContent()+" "+element.getElementsByTagName("ycoord").item(0).getTextContent());
			}
		}
		
		return dblReturn;		
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
