package net.devstudy.config;

import net.devstudy.filter.SimpleFilter3;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class ApplicationConfigInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        FilterRegistration.Dynamic filterConfig = ctx.addFilter("SimpleFilter3",
                new SimpleFilter3());
        filterConfig.addMappingForUrlPatterns(null, true, "/*");
    }
}