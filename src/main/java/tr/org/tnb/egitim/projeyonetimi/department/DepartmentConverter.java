package tr.org.tnb.egitim.projeyonetimi.department;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tr.org.tnb.egitim.projeyonetimi.CommonDao;

@Controller("departmentConverter")
@Scope("request")
public class DepartmentConverter implements Converter {

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value != null && !value.trim().equals("")) {
			Long id = Long.valueOf(value);
			return commonDao.getById(id, Department.class);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object != null) {
			Department department = (Department) object;
			return department.getId().toString();
		}
		return "";
	}

}
