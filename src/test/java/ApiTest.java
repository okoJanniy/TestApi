import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class ApiTest {

    private static final String URL = "https://6107f174d73c6400170d372d.mockapi.io/";
    private static final String DIR = "api/job";
    @Test
    void listUsersTest() {
        given()
                .when()
                .get(URL + DIR)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void singleUserTest() {
        given()
                .when()
                .get(URL + DIR +"/4")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Test
    void createUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"Anton\"," +
                        "\"job\": \"QA\"}")
                .when()
                .post(URL + DIR)
                .then()
                .log().all()
                .statusCode(201).body("job", equalTo("QA"))
                .extract().response();

    }

    @Test
    void successfulCreateUserTest() {
        given()
                .when()
                .get(URL + DIR + "/8")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

    }

    @Test
    public void updateUser() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"Anton\"," +
                        "\"job\": \"Team lead\"}")
                .when()
                .put(URL + DIR + "/8")
                .then()
                .log().all()
                .statusCode(200)
                .body("job", equalTo("Team lead"))
                .extract().response();
    }

    @Test
    void successfulUpdateUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Test
    void successfulDeleteUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();

    }
}