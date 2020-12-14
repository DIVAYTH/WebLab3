import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("inputTextValidator")
public class InputTextValidator implements Validator {
    private FacesMessage ex1 = new FacesMessage("Введите Y от -5 до 3");
    private FacesMessage ex2 = new FacesMessage("Y должен быть числом");

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        try {
            String skipValidator = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("skipValidator");
            if (skipValidator != null) {
                double y = Double.parseDouble((String) o);
                if (y < -5 || y > 3) {
                    throw new ValidatorException(ex1);
                }
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(ex2);
        }
    }
}