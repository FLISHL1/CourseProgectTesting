package org.example.tests;


import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.example.models.*;
import org.example.driver.ConfProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.hamcrest.Matchers.*;

@Feature("Тесты REST API reqres")
public class ReqresBackEndTest {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Step("Инициализация страницы")
    @BeforeEach
    public void init() {
        RestAssured.reset();
        RestAssured.baseURI = ConfProperties.getProperties("reqres");
        // Given
        JsonSchemaValidator.settings = settings()
                .with()
                .jsonSchemaFactory(
                        JsonSchemaFactory
                                .newBuilder()
                                .setValidationConfiguration(ValidationConfiguration
                                        .newBuilder()
                                        .setDefaultVersion(DRAFTV4)
                                        .freeze())
                                .freeze()).
                and().with().checkedValidation(false);

        logger.info("Init uri");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users?page=2")
    @Epic("Test API /api/users?page=2 ")
    public void userFromPage() {
        UserFromPage userFromPage =
                given()
                        .when()
                        .get("api/users?page=2")
                        .then()
                        .log().all()
                        .body(matchesJsonSchemaInClasspath("jsonSchema/user-from-page.json"))
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.first_name", hasItem("Tobias"))
                        .body("data.last_name", hasItem("Funke"))
                        .statusCode(200)
                        .extract()
                        .as(UserFromPage.class);
        logger.info("Get url success");
        Assertions.assertThat(userFromPage.getPage()).isEqualTo(2);
        Assertions.assertThat(userFromPage.getPer_page()).isEqualTo(6);
        Assertions.assertThat(userFromPage.getTotal()).isEqualTo(12);
        Assertions.assertThat(userFromPage.getTotalPages()).isEqualTo(2);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2")
    @Epic("Test API /api/users/2 ")
    public void userGet() {
        UserData userData =
                given()
                        .when()
                        .get("api/users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("jsonSchema/user-data.json"))
                        .extract()
                        .response()
                        .getBody().jsonPath().getObject("data", UserData.class);
        logger.info("Get url success");
        Assertions.assertThat(userData.getId()).isEqualTo(2);
        Assertions.assertThat(userData.getEmail()).isEqualTo("janet.weaver@reqres.in");
        Assertions.assertThat(userData.getFirstName()).isEqualTo("Janet");
        Assertions.assertThat(userData.getLastName()).isEqualTo("Weaver");
        Assertions.assertThat(userData.getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/22")
    @Epic("Test API /api/users/22 ")
    public void userGetFail() {
        Response response = given()
                .when()
                .get("api/users/22")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
        logger.info("Get url success");
        Assertions.assertThat(response.asString()).isEqualTo("{}");

    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown")
    @Epic("Test API /api/unknown")
    public void unknown() {
        ColorFromPage colorFromPage =
                given()
                        .when()
                        .get("api/unknown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("jsonSchema/color-from-page.json"))
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.name", hasItem("true red"))
                        .body("data.year", hasItem(2002))
                        .extract()
                        .as(ColorFromPage.class);
        logger.info("Get url success");
        Assertions.assertThat(colorFromPage.getPage()).isEqualTo(1);
        Assertions.assertThat(colorFromPage.getPer_page()).isEqualTo(6);
        Assertions.assertThat(colorFromPage.getTotal()).isEqualTo(12);
        Assertions.assertThat(colorFromPage.getTotalPages()).isEqualTo(2);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown/2")
    @Epic("Test API /api/unknown/2")
    public void unknownGet() {
        ColorData colorData =
                given()
                        .when()
                        .get("api/unknown/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("jsonSchema/color-data.json"))
                        .extract()
                        .response()
                        .getBody().jsonPath().getObject("data", ColorData.class);
        logger.info("Get url success");
        Assertions.assertThat(colorData.getId()).isEqualTo(2);
        Assertions.assertThat(colorData.getName()).isEqualTo("fuchsia rose");
        Assertions.assertThat(colorData.getYear()).isEqualTo(2001);
        Assertions.assertThat(colorData.getColor()).isEqualTo("#C74375");
        Assertions.assertThat(colorData.getPantoneValue()).isEqualTo("17-2031");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown/23")
    @Epic("Test API /api/unknown/23 ")
    public void unknownGetFail() {
        Response response = given()
                .when()
                .get("api/unknown/23")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
        logger.info("Get url success");
        Assertions.assertThat(response.asString()).isEqualTo("{}");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта создание пользвателя /api/users/2 post")
    @Epic("Test API /api/users post")
    public void userCreate() {
        People people = People.builder()
                .name("Kirill")
                .job("Programming")
                .build();
        PeopleCreate peopleCreate = given()
                .contentType(ContentType.JSON)
                .body(people)
                .when()
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-create.json"))
                .extract()
                .as(PeopleCreate.class);
        logger.info("Get url success");
        Assertions.assertThat(peopleCreate.getName()).isEqualTo(people.getName());
        Assertions.assertThat(peopleCreate.getJob()).isEqualTo(people.getJob());
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта обновление пользвателя /api/users/2 put")
    @Epic("Test API /api/users put")
    public void userUpdate() {
        People people = People.builder()
                .name("Kirill")
                .job("Programming")
                .build();
        PeopleUpdate peopleUpdate = given()
                .contentType(ContentType.JSON)
                .body(people)
                .when()
                .put("api/users/2")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-update.json"))
                .extract()
                .as(PeopleUpdate.class);
        logger.info("Get url success");
        Assertions.assertThat(peopleUpdate.getName()).isEqualTo(people.getName());
        Assertions.assertThat(peopleUpdate.getJob()).isEqualTo(people.getJob());
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта обновление пользвателя /api/users/2 patch")
    @Epic("Test API /api/users patch")
    public void userUpdatePatch() {
        People people = People.builder()
                .name("Kirill")
                .job("Programming")
                .build();
        PeopleUpdate peopleUpdate = given()
                .contentType(ContentType.JSON)
                .body(people)
                .when()
                .patch("api/users/2")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-update.json"))
                .extract()
                .as(PeopleUpdate.class);
        logger.info("Get url success");
        Assertions.assertThat(peopleUpdate.getName()).isEqualTo(people.getName());
        Assertions.assertThat(peopleUpdate.getJob()).isEqualTo(people.getJob());
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2 delete")
    @Epic("Test API /api/users/2 ")
    public void userDelete() {
        Response response = given()
                .when()
                .delete("api/users/2")
                .then()
                .log().all()
                .statusCode(204)
                .extract()
                .response();
        logger.info("Get url success");
        Assertions.assertThat(response.asString()).isEqualTo("");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта регистрации пользвателя /api/register")
    @Epic("Test API /api/register")
    public void userRegister() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        UserRegister userRegister = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/register-success.json"))
                .body("id", not(hasItem(nullValue())))
                .body("token", not(hasItem(nullValue())))
                .extract()
                .as(UserRegister.class);
        logger.info("Get url success");

    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта регистрации пользвателя /api/register с ошибкой")
    @Epic("Test API /api/register с ошибкой")
    public void userRegisterError() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .build();
        String message = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/register")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("jsonSchema/error.json"))
                .extract()
                .response().jsonPath().get("error");
        logger.info("Get url success");
        Assertions.assertThat(message).isEqualTo("Missing password");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта авторизации пользвателя /api/login")
    @Epic("Test API /api/login")
    public void userLogin() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        UserLogin userLogin = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/login")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/login.json"))
                .body("token", not(hasItem(nullValue())))
                .extract()
                .as(UserLogin.class);
        logger.info("Get url success");

    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта авторизации пользвателя /api/login с ошибкой")
    @Epic("Test API /api/login")
    public void userLoginError() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .build();
        String message = (String) given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/login")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("jsonSchema/error.json"))
                .extract()
                .response().jsonPath().get("error");
        logger.info("Get url success");
        Assertions.assertThat(message).isEqualTo("Missing password");

    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта задержки /api/users?delay=3")
    @Epic("Test API /api/users?delay=3")
    public void delay() {
        Long time = given()
                .when()
                .get("api/users?delay=3")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-from-page.json"))
                .extract()
                .timeIn(TimeUnit.SECONDS);
        logger.info("Get url success");
        Assertions.assertThat(time).isBetween(3L, 4L);
    }
}
