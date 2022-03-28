import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;


public class ApiTest {

    private static final String URL = "https://6107f174d73c6400170d372d.mockapi.io/";
    private static final String DIR = "api/job";
    Faker faker = new Faker();
    String name = faker.name().firstName();
    String job = faker.job().position();



    @Test(priority = 1)
    void listUsersTest() {
        given()
                .when()
                .get(URL + DIR)
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test(priority = 2)
    void singleUserTest() {
        given()
                .when()
                .get(URL + DIR +"/4")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test(priority = 3)
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

    @Test(priority = 4)
    void successfulCreateUserTest() {
        given()
                .when()
                .get(URL + DIR + "/8")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();

    }

    @Test(priority = 5)
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
                .assertThat().body("job", equalTo("Team lead"))
                .body("name", equalTo("Anton"))
                .extract().response();
    }

    @Test(priority = 6)
    void successfulUpdateUserTest() {
        given()
                .when()
                .get(URL + "api/job/8")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test(priority = 7)
    public void deleteUser() {
        given()
                .when()
                .delete(URL + "api/job/8")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test(priority = 8)
    void successfulDeleteUserTest() {
        given()
                .when()
                .get(URL + "api/job/9")
                .then()
                .log().all()
                .assertThat().statusCode(404)
                .extract().response();

    }
}