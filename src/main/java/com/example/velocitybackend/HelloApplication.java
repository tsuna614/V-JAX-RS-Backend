package com.example.velocitybackend;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import com.example.velocitybackend.middlewares.CorsFilter;
import com.example.velocitybackend.resources.*;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(AuthResource.class);
        classes.add(BookResource.class);
        classes.add(MessageResource.class);
        classes.add(NotificationResource.class);
        classes.add(PostResource.class);
        classes.add(TravelResource.class);
        classes.add(UserResource.class);
        classes.add(CloudinaryResource.class);

        // NOTE: ADD THIS TO PREVENT CORS ERROR FROM CLIENT
        classes.add(CorsFilter.class);

        return classes;
    }
}