import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainC {
	String strLinea;
	ArrayList<String> getBuses;
	Double[] getCoord = new Double[2];
	static String[] args;
	public static void main(String[] runargs) {
		args = runargs;
		new MainC().initClass();
	}
	public void initClass() {
		if(args[0].equals("-GV")) {
			strLinea = args[1];
			getVehiculos();
		} else if (args[0].equals("-MB")){
			getMunicipios();
		} else if(args[0].equals("-PB")) {
			if(args.length == 2) {
				getParadasBizkaibus();
			} else {
				getParadasMunicipio();
			}			
		} else if(args[0].equals("-GP")) {
			getPasoParada();
		} else if(args[0].equals("-GLL")) {
			getListaLineas();
		} else if(args[0].equals("-GLI")) {
			getInfoLinea();
		} else if(args[0].equals("-GI")){
			getItinerario();
		}
	}
	
	public void getVehiculos() {
		System.out.println("Linea: "+strLinea);
		System.out.println("~^~^~^~^~^~^~^~^~^~^~^~^~^~^~");
		System.out.println("Buses en la linea "+strLinea+": ");
		System.out.println("-----------------------------");
		getBuses = GetVehiculos.getBuses(strLinea);
		for(int i = 0; i<getBuses.size(); i++) {
			System.out.println(getBuses.get(i));			
		}
		System.out.println("=============================");
		System.out.println("Ubicación buses: ");
		System.out.println("-----------------------------");
		getBuses = GetVehiculos.getBuses(strLinea);
		for(int i = 0; i<getBuses.size(); i++) {
			getCoord = GetVehiculos.getCoord(strLinea, getBuses.get(i));
			System.out.println("Ubicacion bus "+getBuses.get(i)+": "+getCoord[0]+" "+getCoord[1]);
		}
		System.out.println("=============================");
	}
	public void getMunicipios() {
		LinkedHashMap<String, String> Mun = new LinkedHashMap<String, String>();
		Mun = GetMunicipios.getMunCod(args[1]);
		for(int i= 0; i<Mun.keySet().toArray().length;i++) {
			System.out.println(Mun.keySet().toArray()[i]+" "+Mun.get(Mun.keySet().toArray()[i]));
		}
		System.out.println(".");
	}
	public void getParadasBizkaibus() {
		LinkedHashMap<String, String> Par = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> Mun = new LinkedHashMap<String, String>();
		Mun = GetMunicipios.getMunCod(args[1]);
		Par = GetParadasBizkaibus.getParadasAll();
		System.out.println("");
		System.out.println("Paradas por municipio: ");
		System.out.println("=============================");
		for(int i= 0; i<Mun.keySet().toArray().length;i++) {
			System.out.println(Mun.keySet().toArray()[i]+":");
			System.out.println("-----------------------------");
			for(int e= 0; e<Par.keySet().toArray().length;e++) {
				if(Par.keySet().toArray()[e].toString().startsWith(args[1]+Mun.get(Mun.keySet().toArray()[i]))) {
					System.out.println(Par.keySet().toArray()[e]+": "+Par.get(Par.keySet().toArray()[e]));
				}
			}
			System.out.println("=============================");
		}
	}
	public void getParadasMunicipio() {
		LinkedHashMap<String, String> Par = new LinkedHashMap<String, String>();
		Par = GetParadasBizkaibus.getParadasMunicipio(args[1]);
		for(int i = 0; i<Par.keySet().toArray().length; i++) {
			System.out.println(Par.keySet().toArray()[i]+": "+Par.get(Par.keySet().toArray()[i]));
		}
	}
	
	public void getPasoParada() {
		LinkedHashMap<String, ArrayList<String>> Info = new LinkedHashMap<String, ArrayList<String>>();
		Info = GetPasoParada.getInfo(args[1]);
		System.out.println("Parada numero: "+args[1]);
		System.out.println("=============================");
		for(int i = 0; i<Info.size(); i++) {
			ArrayList<String> list = new ArrayList<String>();
			list = Info.get(Info.keySet().toArray()[i]);
			System.out.println("	Linea: "+ Info.keySet().toArray()[i]);
			System.out.println("1.BUS: Destino: "+list.get(0)+". Distancia: "+list.get(1)+"m. Faltan: "+list.get(2)+"minutos.");
			System.out.println("2.BUS: Destino: "+list.get(3)+". Distancia: "+list.get(4)+"m. Faltan: "+list.get(5)+"minutos.");
		}
		
	}
	
	public void getListaLineas() {
		System.out.println("Lista de lineas:");
		System.out.println("=============================");
		GetLineas.load();		
		ArrayList<String> aList = new ArrayList<>();
		aList = GetLineas.getLineas();
		for(int i = 0; i<aList.size();i++) {
			System.out.println(aList.get(i));
		}
	}
	
	public void getInfoLinea() {
		System.out.println("Nombre de la linea "+args[1]+":");
		System.out.println("=============================");
		GetLineas.load();
		System.out.println(GetLineas.getNombre(args[1]));
		System.out.println("-----------------------------");
		System.out.println("Incidencia Castellano:");
		System.out.println(GetLineas.getIncCas(args[1]));
		System.out.println("-----------------------------");
		System.out.println("Incidencia Euskera:");
		System.out.println(GetLineas.getIncEus(args[1]));
		System.out.println("-----------------------------");
		System.out.println("Horario Castellano:");
		System.out.println(GetInfoLinea.getHorarioIC(args[1], ""));
		System.out.println("Caducidad Horario");
		System.out.println(GetInfoLinea.caducidadHorarioUnix(args[1]));
	}
	
	public void getItinerario() {
		System.out.println("Nombre de la linea "+args[1]+":");
		System.out.println("-----------------------------");
		LinkedHashMap<String, ArrayList<String>> Itin = new LinkedHashMap<String, ArrayList<String>>();
		Itin = GetInfoLinea.getItinerario(args[1], args[2]);
		for(int i = 0; i<Itin.size(); i++) {
			ArrayList<String> list = new ArrayList<String>();
			list = Itin.get(Itin.keySet().toArray()[i]);
			System.out.println("Parada: "+list.get(0));
			System.out.println("Municipio: "+list.get(1));
			System.out.println("Coord: "+list.get(2)+", "+list.get(3));
		}
	}
	/*parada ayunta  Nº 1257 -> 48096023 
		-48 Bizkaia
		-096 Nº Zalla. Buscar en 
		-023 Nº parada de zalla. buscar en gfReturn_Consultar_TBTQ0001_PARADAS_BIZKAIBUS con 4, 48, 096, "vacio" y buscar zalla
		
		para sacar Nº para de bizkaibus, no zalla, es 
		<Consulta>
			<Registro COORDENADA_X="489125.0000000000" COORDENADA_Y="4784617.0000000000" PROVINCIA="48" MUNICIPIO="096" DescripcionMunicipio="ZALLA" PARADA="023" DENOMINACION="Mimetiz (Udaletxea/Ayuntamiento)" OBJECTID="1155849" />
		</Consulta>
	*/
}
