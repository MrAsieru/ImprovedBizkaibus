import java.util.LinkedHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 * Solamente llamar GetMunicipios.getMunCod()
 * @author MrAsieru
 *
 */
public class GetMunicipios {
	static String uri = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/gfReturn_Consultar_MunicipiosBizkaia_y_Colindantes";
	public GetMunicipios() {		
	}
	
	/*Estructura:
	 * 	<Consulta>
	 * 		<Registro CodigoProvincia="48" DescripcionProvincia="BIZKAIA" CodigoMunicipio="96" DescripcionMunicipio="ZALLA" />
	 * 	</Consulta>
	 * 
	 */
	/**
	 * @param String strProvincia numero de provincia: 1 Alava, 20 Gipuzkoa, 48 Bizkaia, 39 Cantabria
	 * @return LinkedHashMap<String, String> Devuelve Key: Municipio; Valor: CodigoMunicipio
	 * (ej.Zalla, 96)
	 */
	public static LinkedHashMap<String, String> getMunCod(String strProvincia) {
		Document doc = FromXml.getDoc(uri);
		LinkedHashMap<String, String> Mun = new LinkedHashMap<String, String>();
		NodeList nodeList = doc.getElementsByTagName("Registro");
		for(int i = 0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			Element element = (Element) node;
			if(element.getAttribute("CodigoProvincia").equals(strProvincia)) {
				Mun.put(element.getAttribute("DescripcionMunicipio"), String.format("%03d",Integer.parseInt( element.getAttribute("CodigoMunicipio"))));
			}			
		}	
		return Mun;
		
	}
}
