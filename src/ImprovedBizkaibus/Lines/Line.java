package ImprovedBizkaibus.Lines;

public class Line {
	private String id;
	private String name;
	private String espEvent;
	private String eusEvent;
	private boolean montesHierro;

	public Line(String pId, String pName, String pEspEvent, String pEusEvent, boolean pMontesHierro){
		this.id = pId;
		this.name = pName;
		this.espEvent = pEspEvent;
		this.eusEvent = pEusEvent;
		this.montesHierro = pMontesHierro;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEspEvent() {
		return espEvent;
	}

	public String getEusEvent() {
		return eusEvent;
	}

	public boolean isMontesHierro() {
		return montesHierro;
	}
}
