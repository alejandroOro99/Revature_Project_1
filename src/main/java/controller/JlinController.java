package controller;
import io.javalin.Javalin;

public class JlinController {
    public static Javalin startJlin(){
        return Javalin.create(config->config.enableCorsForAllOrigins()).start(9000);//returns Javalin object that starts Jetty server(localhost:9000)
    }
}
