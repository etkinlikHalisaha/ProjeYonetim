package tr.org.tnb.egitim.projeyonetimi;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tr.org.tnb.egitim.projeyonetimi.employee.PhoneType;

@Controller
@Scope("singleton")
public class CommonDataController {
	private List<PhoneType> phoneTypeList;

	@PostConstruct
	public void init() {
		this.phoneTypeList = Arrays.asList(PhoneType.values());
	}
	
	
	public List<PhoneType> getPhoneTypeList() {
		return phoneTypeList;
	}
	
	
}
