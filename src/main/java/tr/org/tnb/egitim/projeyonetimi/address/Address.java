package tr.org.tnb.egitim.projeyonetimi.address;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;

@Entity
@Table(name = "t_address")
public class Address extends BaseEntity {
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Transient
	public String getFullAddress() {
		return state + " " + city + " " + street + " " + zip; 
	}
	
	@Transient
	public boolean isValid() {
		return !StringUtils.isEmpty(street) ||
				!StringUtils.isEmpty(city) ||
				!StringUtils.isEmpty(state) ||
				!StringUtils.isEmpty(zip);
	}
	
}
