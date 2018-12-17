package ex.config;

import org.apache.wicket.protocol.http.WicketFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(value = "/*", initParams = {
        @WebInitParam(name = "applicationClassName",
                value = "ExorigoUposTask"),
        @WebInitParam(name = "configuration", value = "development")})
public class AppFilter extends WicketFilter {
}
