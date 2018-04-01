package nimor111.com.example.jersey;

import com.github.javafaker.Faker;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserResourceTest extends FunctionalTest {
    Faker faker = new Faker();

    @Test
    public void getUsersReturn200() {
        given().when().get("/users/get").then().statusCode(200);
    }

    @Test
    public void wrongEmailFormatReturns404() {
        given().when().get("/users/get/123").then().statusCode(404);
    }

    @Test
    public void creatingUserWithValidDataReturns200() {
        Map<String, String> user = new HashMap<String, String>();
        user.put("firstName", this.faker.name().firstName());
        user.put("lastName", this.faker.name().lastName());
        user.put("email", this.faker.name().firstName() + "@" + this.faker.name().lastName() + ".com");
        user.put("role", "User");

        given().contentType("application/json").body(user).when().post("/users/create").then().statusCode(200);
    }

    @Test
    public void updatingAdminAccountNotAllowed() {
        Map<String, String> user = new HashMap<String, String>();
        user.put("firstName", this.faker.name().firstName());
        user.put("lastName", this.faker.name().lastName());
        user.put("email", this.faker.name().firstName() + "@" + this.faker.name().lastName() + ".com");
        user.put("role", "Administrator");

        given()
            .contentType("application/json")
            .body(user)
            .when()
            .put("/users/update")
            .then()
            .statusCode(401);
    }


    @Test
    public void updatingUserReturns200AndIsSuccessful() {
        Map<String, String> user = new HashMap<String, String>();
        user.put("firstName", this.faker.name().firstName());
        user.put("lastName", this.faker.name().lastName());
        user.put("email", this.faker.name().firstName() + "@" + this.faker.name().lastName() + ".com");
        user.put("role", "User");

        given()
            .contentType("application/json")
            .body(user)
            .when()
            .post("/users/create")
            .then()
            .statusCode(200);

        Map<String, String> updatedUser = new HashMap<String, String>();
        String newName = this.faker.name().firstName();
        updatedUser.put("firstName", newName);
        updatedUser.put("lastName", user.get("lastName"));
        updatedUser.put("email", user.get("email"));
        updatedUser.put("role", user.get("role"));

        given()
            .contentType("application/json")
            .body(updatedUser)
            .when()
            .put("/users/update")
            .then()
            .body("firstName", equalTo(newName))
            .statusCode(200);
    }

    @Test
    public void deletingUserReturns200() {
        Map<String, String> user = new HashMap<String, String>();
        user.put("firstName", this.faker.name().firstName());
        user.put("lastName", this.faker.name().lastName());
        user.put("email", this.faker.name().firstName() + "@" + this.faker.name().lastName() + ".com");
        user.put("role", "User");

        given().contentType("application/json")
            .body(user)
            .when()
            .post("/users/create")
            .then()
            .statusCode(200);

        given().contentType("application/json")
            .when()
            .delete("/users/delete/" + user.get("email"))
            .then()
            .statusCode(200);

        given()
            .when()
            .get("/users/get/" + user.get("email"))
            .then()
            .statusCode(404);
    }
}
