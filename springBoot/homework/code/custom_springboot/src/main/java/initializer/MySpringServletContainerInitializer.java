package initializer;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Method;
import java.util.Set;

public class MySpringServletContainerInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        if (set != null) {
            for (Class<?> c : set) {
                if (WebApplicationInitializer.class.isAssignableFrom(c)) {
                    try {
                        Object o = c.getDeclaredConstructor().newInstance();
                        Method onStartup = c.getDeclaredMethod("onStartup");
                        onStartup.invoke(o, servletContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
