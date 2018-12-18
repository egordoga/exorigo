package ex.wicket;


import ex.entity.User;
import ex.service.UserService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginForm extends Form<LoginPage> {

    @SpringBean
    private UserService userService;

    private TextField<String> nameField;
    private TextField<String> lastNameField;
    private PasswordTextField passField;

    public LoginForm(String id) {
        super(id);

        nameField = new TextField<>("name", Model.of(""));
        lastNameField = new TextField<>("lastName", Model.of(""));
        passField = new PasswordTextField("pass", Model.of(""));

        add(nameField);
        add(lastNameField);
        add(passField);
    }

    public void onSubmit() {
        String name = nameField.getDefaultModelObjectAsString();
        String lastName = lastNameField.getDefaultModelObjectAsString();
        String pass = passField.getDefaultModelObjectAsString();
        User user = new User(name, lastName, pass);
        userService.addUser(user);
    }
}
