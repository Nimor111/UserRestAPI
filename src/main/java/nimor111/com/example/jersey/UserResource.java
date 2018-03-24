package nimor111.com.example.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {
    private final UserRepository repo = new UserRepository();

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getUsers() {
        return repo.getUsers();
    }

    @GET
    @Path("/get/{email}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("email") String email) {
        User user = repo.getUser(email);

        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return user;
    }

    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User createUser(User user) {
        if (repo.createUser(user) == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return user;
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User updateUser(User user) {
        if (repo.getUser(user.getEmail()) == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
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
        if (user != null) {
            repo.deleteUser(email);

            return user;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

}
