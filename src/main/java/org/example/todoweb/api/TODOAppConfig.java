package org.example.todoweb.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class TODOAppConfig extends ResourceConfig
{
    public TODOAppConfig()
    {
        packages("org.example.todoweb.api");
    }
}
