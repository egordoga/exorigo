package ex.config;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextLoaderListener extends ContextLoaderListener {
    public AppContextLoaderListener() {
        super(getWebApplicationContext());
    }

    private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.scan("ex");
        context.refresh();
        return context;
    }
}
