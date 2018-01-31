package tr.org.tnb.egitim.projeyonetimi.employee;

public enum PhoneType {
	H("Home"),
	C("Cell"),
	O("Office");
	
	private String label;

	public String getLabel() {
		return label;
	}
	
	private PhoneType(String label) {
		this.label = label;
	}
	
}
