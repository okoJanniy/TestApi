import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class ApiTest {

    private static final String URL = "https://6107f174d73c6400170d372d.mockapi.io/";
    @Test
    void listUsersTest() {
        given()
                .when()
                .get(URL + "api/job")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void singleUserTest() {
        given()
                .when()
                .get(URL + "api/job/4")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    void createUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"Anton\"," +
                        "\"job\": \"QA\"}")
                .when()
                .post(URL + "api/job")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("Anton"))
                .body("job", is("QA"));
    }
    @Test
    void successfulCreateUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    public void updateUser() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"Anton\"," +
                        "\"job\": \"Team lead\"}")
                .when()
                .put(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Anton"))
                .body("job", is("Team lead"))
        ;
    }
    @Test
    void successfulUpdateUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    public void deleteUser() {
        given()
                .when()
                .delete(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(200)
        ;
    }
    @Test
    void successfulDeleteUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(404);
    }
}