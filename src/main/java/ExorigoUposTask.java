import ex.wicket.UserPage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class ExorigoUposTask  extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return UserPage.class;
    }
    @Override
    protected void init() {
        super.init();
        getDebugSettings().setAjaxDebugModeEnabled(false);

        getComponentInstantiationListeners().add(new SpringComponentInjector(this,
                WebApplicationContextUtils.getRequiredWebApplicationContext(
                        getServletContext())));
    }
}
