import java.util.LinkedHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 * Solamente llamar a los metodos.
 * @author MrAsieru
 *
 */
public class GetParadasBizkaibus {
	static String uri = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/gfReturn_Consultar_TBTQ0001_PARADAS_BIZKAIBUS"+
						"?intTipoConsulta=@tp"+
						"&strCodigoProvinvia_Parada=@cpr"+
						"&strCodigoMunicipio_Parada=@cm"+
						"&strCodigoParada_Parada=@cpa";
	public GetParadasBizkaibus() {
	}
	/*Estructura: 
	 * 	<Consulta>
	 *		<Registro 	COORDENADA_X="500200.0000000000" 
	 *					COORDENADA_Y="4767403.0000000000" 
	 *					PROVINCIA="01" 
	 *					MUNICIPIO="002" 
	 *					DescripcionMunicipio="AMURRIO" 
	 *					PARADA="015" 
	 *					DENOMINACION="San José Auzoa" 
	 *					OBJECTID="1154576" />
	 * 	</Consulta>
	 */
	
	/**
	 * 
	 * @param strCodigoMunicipio Introduce el numero de municipio(en String !) (ej. "096" -> Numero de Zalla)
	 * @return LinkedHashMap<String, String> Devuelve Map con Key: strParada y Value:strMunicipio
	 */
	public static LinkedHashMap<String, String> getParadasMunicipio(String strCodigoMunicipio ) {
		LinkedHashMap<String, String> Par = new LinkedHashMap<String, String>();
		String url = uri;
		url = url.replace("@tp", "2");
		url = url.replace("@cpr", "");
		url = url.replace("@cm", "");
		url = url.replace("@cpa", "");
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			if(element.getAttribute("MUNICIPIO").equals(strCodigoMunicipio)) {
				Par.put(element.getAttribute("PROVINCIA")+element.getAttribute("MUNICIPIO")+element.getAttribute("PARADA"), element.getAttribute("DENOMINACION"));
			}
		}
		return Par;
	}
	
	/**
	 * Llamar para lista completa de paradas de Bizkaibus
	 * @return LinkedHashMap con Key: strParada y Value: strMunicipio
	 */
	public static LinkedHashMap<String, String> getParadasAll() {
		LinkedHashMap<String, String> Par = new LinkedHashMap<String, String>();
		String url = uri;
		url = url.replace("@tp", "2");
		url = url.replace("@cpr", "");
		url = url.replace("@cm", "");
		url = url.replace("@cpa", "");
		Document doc = FromXml.getDoc(url);
		NodeList nodeList = doc.getElementsByTagName("Registro");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			Par.put(element.getAttribute("PROVINCIA")+element.getAttribute("MUNICIPIO")+element.getAttribute("PARADA"), element.getAttribute("DENOMINACION"));
		}
		return Par;
	}
}
