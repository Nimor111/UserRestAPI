package nimor111.com.example.jersey.resources;

import nimor111.com.example.jersey.models.User;
import nimor111.com.example.jersey.database.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {
    private final UserRepository repo = new UserRepository();

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUsers() {
        List<User> users = repo.getUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};

        return Response
                .ok(entity)
                .build();
    }

    @GET
    @Path("/get/{email}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUser(@PathParam("email") String email) {
        User user = repo.getUser(email);

        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response
                .ok(user)
                .build();
    }

    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUser(User user) {
        if (repo.createUser(user) == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return Response
                .ok(user)
                .build();
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUser(User user) {
        if(user.getRole().toString() == "Administrator") {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        if (repo.getUser(user.getEmail()) == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            repo.updateUser(user);
        }

        return Response
                .ok(user)
                .build();
    }

    @DELETE
    @Path("/delete/{email}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteUser(@PathParam("email") String email) {
        User user = repo.getUser(email);
        if (user != null) {
            repo.deleteUser(email);

            return Response
                    .ok(user)
                    .build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

}
