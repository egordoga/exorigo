package ex.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LoginPage extends WebPage {

    public LoginPage(final PageParameters parameters) {
        super(parameters);
        add(new LoginForm("loginForm"));

        Link link = new Link("link") {    //Здесь высвечивает ошибку, однако программа "билдится" и работает нормально
            @Override
            public void onClick() {
                setResponsePage(UserPage.class);
            }
        };
        add(link);
    }
}
