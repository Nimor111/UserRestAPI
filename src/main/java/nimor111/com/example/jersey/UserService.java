package nimor111.com.example.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserService {
    UserRepository repo = new UserRepository();

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        List<User> users = repo.getUsers();

        return users;
    }
}
