import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;



public class ApiTest {

    private static final String URL = "https://6107f174d73c6400170d372d.mockapi.io/";
    private static final String DIR = "api/job";
    Faker faker = new Faker();
    String name = faker.name().firstName();
    String job = faker.job().position();



    @Test
    void listUsersTest() {
        given()
                .when()
                .get(URL + DIR)
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    void singleUserTest() {
        given()
                .when()
                .get(URL + DIR +"/4")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test
    void createUserTest() {
        given()
                .body(name + job)
                .when()
                .post(URL + DIR)
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .body("id", equalTo("8"))
                .extract().response();
    }

    @Test
    void successfulCreateUserTest() {
        given()
                .when()
                .get(URL + DIR + "/8")
                .then()
                .log().all()
                .assertThat().statusCode(200)
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
                .assertThat().statusCode(200)
                .body("job", equalTo("Team lead"))
                .body("name", equalTo("Anton"))
                .extract().response();
    }

    @Test
    void successfulUpdateUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete(URL + "api/job/8")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test
    void successfulDeleteUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .assertThat().statusCode(404)
                .extract().response();

    }
}