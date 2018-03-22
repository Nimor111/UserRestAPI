package nimor111.com.example.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorld {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Hello getText() {
        Hello hello = new Hello();
        hello.setText("Hello world!");
        return hello;
    }
}
