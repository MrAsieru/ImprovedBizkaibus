import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GetPasoParada {
	static String uri = "http://apli.bizkaia.net/APPS/DANOK/TQWS/TQ.ASMX/GetPasoParada?strLinea=&strParada=";
	public GetPasoParada() {
	}
	/*Estructura: 
	 * 	<GetPasoParadaResult>
	 * 		<PasoParada>
	 * 			<linea>A3334</linea>
	 * 			<ruta>BALMASEDA</ruta>
	 * 			<e1>
	 * 				<destino>BALMASEDA</destino>
	 * 				<metros>14305</metros>
	 * 				<minutos>34</minutos>
	 * 			</e1>
	 * 			<e2>
	 * 				<destino></destino>
	 * 				<metros></metros>
	 * 				<minutos></minutos>
	 * 			</e2>
	 * 		</PasoParada>
	 * 		<PasoParada>
	 * 			<linea>A0651</linea>
	 * 			<ruta>BALMASEDA</ruta>
	 * 			<e1>
	 * 				<destino>BALMASEDA</destino>
	 * 				<metros>17878</metros>
	 *				<minutos>32</minutos>
	 * 			</e1>
	 * 			<e2>
	 * 				<destino>BALMASEDA</destino>
	 * 				<metros>50354</metros>
	 * 				<minutos>93</minutos>
	 * 			</e2>
	 * 		</PasoParada>
	 * 	</GetPasoParadaResult>
	 */
	public static LinkedHashMap<String, ArrayList<String>> getInfo(String strParada) {
		LinkedHashMap<String, ArrayList<String>> Info = new LinkedHashMap<String, ArrayList<String>>();
		Document doc = FromXml.getDoc(uri+strParada);
		NodeList PasoParNL = doc.getElementsByTagName("PasoParada");
		NodeList[] s = new NodeList[2];
		for(int i = 0; i<PasoParNL.getLength(); i++) {
			//Cada Linea diferente ej. A0651
			org.w3c.dom.Node nodeGen = PasoParNL.item(i);
			Element elementGen = (Element) nodeGen;
			
			ArrayList<String> list = new ArrayList<>();
			s[0] = elementGen.getElementsByTagName("e1");
			s[1] = elementGen.getElementsByTagName("e2");
			for(int e = 0; e<s.length; e++) {
				//e1 y e2
				org.w3c.dom.Node node = s[e].item(0);
				Element element = (Element) node;
				if(element.getElementsByTagName("destino") != null) {
					list.add(element.getElementsByTagName("destino").item(0).getTextContent());
				} else {
					list.add("");
				}
				if(element.getElementsByTagName("metros") != null) {
					list.add(element.getElementsByTagName("metros").item(0).getTextContent());
				} else {
					list.add("");
				}
				if(element.getElementsByTagName("minutos") != null) {
					list.add(element.getElementsByTagName("minutos").item(0).getTextContent());
				} else {
					list.add("");
				}
				Info.put(elementGen.getElementsByTagName("linea").item(0).getTextContent(), list);
			}		
		}
		return Info;
	}
}
