package ex.config;

import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

@WebListener
public class AppRequestContextListener extends RequestContextListener {
}
