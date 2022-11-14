package ir.jimsa.user.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    private final Environment environment;

    @Autowired
    public AppProperties(Environment environment) {
        this.environment = environment;
    }

    public String getTokenSecret() {
        return environment.getProperty("tokenSecret");
    }
}
