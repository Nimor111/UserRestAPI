package nimor111.com.example.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserService {
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        User user = new User();
        user.setFirstName("Georgi");
        user.setLastName("Bojinov");
        user.setEmail("georgi.bojinov@hotmail.com");
        user.setPassword("asdf");
        user.setRole(Role.User);
        return user;
    }
}
