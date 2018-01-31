package tr.org.tnb.egitim.util;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class JsfUtils {
    public static void clearComponents() {
        clearComponentHierarchy(FacesContext.getCurrentInstance().getViewRoot());
    }

    public static void clearComponents(String id) {
        clearComponentHierarchy(FacesContext.getCurrentInstance().getViewRoot().findComponent(id));
    }

    public static void clearComponentHierarchy(UIComponent pComponent) {

        if (pComponent instanceof EditableValueHolder) {
            EditableValueHolder editableValueHolder = (EditableValueHolder) pComponent;
            editableValueHolder.setValid(true);
            editableValueHolder.resetValue();
        }

        for (UIComponent child : pComponent.getChildren()) {
            clearComponentHierarchy(child);
        }
    }
}
