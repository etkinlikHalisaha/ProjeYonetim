package tr.org.tnb.egitim.projeyonetimi.address;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tr.org.tnb.egitim.projeyonetimi.CommonDao;

@Controller("addressConverter")
@Scope("request")
public class AddressConverter implements Converter {

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value != null && !value.trim().equals("")) {
			Long id = Long.valueOf(value);
			return commonDao.getById(id, Address.class);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object != null) {
			Address address = (Address) object;
			if (address.getId() != null) {
				return address.getId().toString();
			}
		}
		return "";
	}

}
