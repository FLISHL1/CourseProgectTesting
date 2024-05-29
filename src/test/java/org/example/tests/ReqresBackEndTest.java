package org.example.tests;


import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.example.models.*;
import org.example.driver.ConfProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Feature("Тесты REST API reqres")
public class ReqresBackEndTest  {


    @Step("Инициализация страницы")
    @BeforeEach
    public void init(){
        RestAssured.reset();
        RestAssured.baseURI = ConfProperties.getProperties("reqres");

    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users?page=2")
    @Epic("Test API /api/users?page=2 ")
    public void userFromPage(){
        UserFromPage userFromPage =
                given()
                        .when()
                        .get("api/users?page=2")
                        .then()
                        .log().all()
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.first_name", hasItem("Tobias"))
                        .body("data.last_name", hasItem("Funke"))
                        .statusCode(200)
                        .extract()
                        .as(UserFromPage.class);
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
    public void userGet(){
        UserData userData =
                given()
                        .when()
                        .get("api/users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response()
                        .getBody().jsonPath().getObject("data", UserData.class);

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
    public void userGetFail(){
        Response response = given()
                        .when()
                        .get("api/users/22")
                        .then()
                        .log().all()
                        .statusCode(404)
                        .extract()
                .response();
        Assertions.assertThat(response.asString()).isEqualTo("{}");

    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown")
    @Epic("Test API /api/unknown")
    public void unknown(){
        ColorFromPage colorFromPage =
                given()
                        .when()
                        .get("api/unknown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.name", hasItem("true red"))
                        .body("data.year", hasItem(2002))
                        .extract()
                        .as(ColorFromPage.class);
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
    public void unknownGet(){
        ColorData colorData =
                given()
                        .when()
                        .get("api/unknown/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response()
                        .getBody().jsonPath().getObject("data", ColorData.class);

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
    public void unknownGetFail(){
        Response response = given()
                .when()
                .get("api/unknown/23")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
        Assertions.assertThat(response.asString()).isEqualTo("{}");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта создание пользвателя /api/users/2 post")
    @Epic("Test API /api/users post")
    public void userCreate(){
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
                .extract()
                .as(PeopleCreate.class);
        Assertions.assertThat(peopleCreate.getName())
                .isEqualTo(people.getName());
        Assertions.assertThat(peopleCreate.getJob())
                .isEqualTo(people.getJob());
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта обновление пользвателя /api/users/2 put")
    @Epic("Test API /api/users put")
    public void userUpdate(){
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
                .extract()
                .as(PeopleUpdate.class);
        Assertions.assertThat(peopleUpdate.getName()).isEqualTo(people.getName());
        Assertions.assertThat(peopleUpdate.getJob()).isEqualTo(people.getJob());
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта обновление пользвателя /api/users/2 patch")
    @Epic("Test API /api/users patch")
    public void userUpdatePatch(){
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
                .extract()
                .as(PeopleUpdate.class);
        Assertions.assertThat(peopleUpdate.getName()).isEqualTo(people.getName());
        Assertions.assertThat(peopleUpdate.getJob()).isEqualTo(people.getJob());
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2 delete")
    @Epic("Test API /api/users/2 ")
    public void userDelete(){
        Response response = given()
                .when()
                .delete("api/users/2")
                .then()
                .log().all()
                .statusCode(204)
                .extract()
                .response();
        Assertions.assertThat(response.asString()).isEqualTo("");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта регистрации пользвателя /api/register")
    @Epic("Test API /api/register")
    public void userRegister(){
        User user = User.builder()
                .email("kirill15022005@gmail.com")
                .password("15022005")
                .build();
        UserRegister userRegister = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .body("id", not(hasItem(nullValue())))
                .body("token", not(hasItem(nullValue())))
                .extract()
                .as(UserRegister.class);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта регистрации пользвателя /api/register с ошибкой")
    @Epic("Test API /api/register с ошибкой")
    public void userRegisterError(){
        User user = User.builder()
                .email("kirill15022005@gmail.com")
                .build();
        String message = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/register")
                .then()
                .statusCode(400)
                .extract()
                .response().jsonPath().get("error");
        Assertions.assertThat(message).isEqualTo("Missing password");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта авторизации пользвателя /api/login")
    @Epic("Test API /api/login")
    public void userLogin(){
        User user = User.builder()
                .email("kirill15022005@gmail.com")
                .password("15022005")
                .build();
        UserLogin userLogin = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/login")
                .then()
                .statusCode(200)
                .body("token", not(hasItem(nullValue())))
                .extract()
                .as(UserLogin.class);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта авторизации пользвателя /api/login с ошибкой")
    @Epic("Test API /api/login")
    public void userLoginError(){
        User user = User.builder()
                .email("kirill15022005@gmail.com")
                .build();
        String message = (String) given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/login")
                .then()
                .statusCode(400)
                .extract()
                .response().jsonPath().get("error");
        Assertions.assertThat(message).isEqualTo("Missing password");

    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта задержки /api/users?delay=3")
    @Epic("Test API /api/users?delay=3")
    public void delay(){
        Long time = given()
                .when()
                .get("api/users?delay=3")
                .then()
                .statusCode(200)
                .extract()
                .timeIn(TimeUnit.SECONDS);
        Assertions.assertThat(time).isBetween(3L, 4L);
    }
}
