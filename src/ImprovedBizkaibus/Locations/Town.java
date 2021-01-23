package ImprovedBizkaibus.Locations;

public class Town {
	private Province province;
	private String id;
	private String name;

	public Town(Province pProvince, String pId, String pName){
		this.province = pProvince;
		this.id = pId;
		this.name = pName;
	}

	public Province getProvince() {
		return province;
	}

	public String getRelativeId() {
		return id;
	}

	public String getFullId(){
		return province.getId()+id;
	}

	public String getName() {
		return name;
	}
}
