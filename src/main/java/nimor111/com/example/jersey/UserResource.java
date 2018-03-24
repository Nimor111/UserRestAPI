package nimor111.com.example.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserResource {
    UserRepository repo = new UserRepository();

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getUsers() {
        List<User> users = repo.getUsers();

        return users;
    }

    @GET
    @Path("/get/{email}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("email") String email) {
        User user = repo.getUser(email);

        return user;
    }

    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User createUser(User user) {
        repo.createUser(user);

        return user;
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User updateUser(User user) {
        if (repo.getUser(user.getEmail()) == new User()) {
            repo.createUser(user);
        } else {
            repo.updateUser(user);
        }

        return user;
    }

    @DELETE
    @Path("/delete/{email}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User deleteUser(@PathParam("email") String email) {
        User user = repo.getUser(email);
        if (user != new User()) {
            repo.deleteUser(email);
        }

        return user;
    }

}
