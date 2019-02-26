import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class FromXml {
	public FromXml() {				
	}
	
	static public Document getDoc(String uri) {
		URL url = null;
		Scanner xmlns = null;
		String data = "";
		Document doc = null;
		try {
			url = new URL(uri);
			xmlns = new Scanner(url.openStream());
			while(xmlns.hasNextLine()) {
				data = data+xmlns.nextLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		data = data.replace("&lt;string xmlns=\"http://tempuri.org/LANTIK_TQWS/TQ\">", "");
		data = data.replace("&lt;/string&gt;", "");
		data = data.replace("({'STATUS': 'OK','Resultado': '","");
		data = data.replace("','Info': '','NoInfo': '','Error': '','Metodo': 'GetLineas_JSON'});","");
		data = data.replace("1®", "");
		data = data.replace("&lt;", "<");
		data = data.replace("&gt;", ">");
		data = data.replace("Ã‘","Ñ");
		data = data.replace("Ã©","é");
		data = data.replace("Ã±","ñ");
		data = data.replace("Ã³","ó");
		data = data.replace("Ã­","í");
		data = data.replace("Ã¡","á");
		data = data.replace("Âº","º");
		data = data.replace("{vbNewLine}", "\n");
		try {
			doc = loadXMLFromString(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	private static Document loadXMLFromString(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
}
