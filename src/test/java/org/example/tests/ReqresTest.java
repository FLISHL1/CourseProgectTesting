package org.example.tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.driver.TestListener;
import org.example.models.People;
import org.example.models.User;
import org.example.pageTemplate.ReqresPage;
import org.junit.jupiter.api.Disabled;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Тесты сайта reqres")
public class ReqresTest extends DriverSetup {
    public static ReqresPage reqresPage;
    private static final Logger logger = LoggerFactory.getLogger(ReqresTest.class);

    @Step("Инициализация страницы")
    public void init(String id){
        logger.info("Start test page reqres endpoint " + id);
        reqresPage = new ReqresPage(driver);
        driver.get(ConfProperties.getProperties("reqres"));
        RestAssured.reset();
        RestAssured.baseURI = ConfProperties.getProperties("reqres");
        logger.info("Page get success");
        reqresPage.setEndPoint(id);
        logger.info("Select endpoint");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Инициализация страницы")
    public void init(String id, boolean check){
        logger.info("Start test page reqres endpoint " + id);
        reqresPage = new ReqresPage(driver);
        driver.get(ConfProperties.getProperties("reqres"));
        logger.info("Page get success");
        reqresPage.setEndPoint(id);
        logger.info("Select endpoint");

    }


    @Step("Проверка request")
    public void stepCheckRequest(String name){
        assertEquals(name, reqresPage.getLabelRequest());
        logger.info("Checked request");
    }

    @Step("Проверка задержки появления")
    public void stepCheckDelay(Integer delay){
        logger.info("Start check delay");
        assertTrue(reqresPage.checkVisibleResponseCode(delay));
        logger.info("End check delay");
    }

    @Step("Проверка response")
    public void stepCheckResponse(Integer num){
        assertEquals(num, Integer.parseInt(reqresPage.getLabelResponse()));
        logger.info("Checked response");
    }

    @Step("Проверка responseCode")
    public void stepCheckResponseCode(String code, boolean regexp){
        if (regexp)
        assertEquals(code
                .replaceAll("\\d+-\\d+-\\d+T\\d{2}:\\d{2}:\\d{2}.\\d+Z", "")
                .replaceAll("\\d{3}", ""), reqresPage.getLabelResponseCode()
                .replaceAll("\\d+-\\d+-\\d+T\\d{2}:\\d{2}:\\d{2}.\\d+Z", "").replaceAll("\\d{3}", ""));
        else assertEquals(code, reqresPage.getLabelResponseCode());
        logger.info("Checked responseCode");
    }


    @Step("Проверка requestCode")
    public void stepCheckRequestCode(String response){
        assertEquals(response, reqresPage.getLabelRequestCode());
        logger.info("Checked response");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users?page=2")
    @Description("")
    @Epic("Test /api/users?page=2 ")
    public void test1(){
        init("users");
        stepCheckRequest("/api/users?page=2");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/users?page=2").then().extract().asPrettyString(), false);
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2")
    @Description("")
    @Epic("Test /api/users/2 ")
    public void test2(){
        init("users-single");
        stepCheckRequest("/api/users/2");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/users/2").then().extract().asPrettyString(), false);
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/23")
    @Description("")
    @Epic("Test /api/users/23 ")
    public void test3(){
        init("users-single-not-found");
        stepCheckRequest("/api/users/23");
        stepCheckResponse(404);
        stepCheckResponseCode(given().when().get("api/users/23").then().extract().asString(), false);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown")
    @Description("")
    @Epic("Test /api/unknown ")
    public void test4(){
        init("unknown");
        stepCheckRequest("/api/unknown");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/unknown").then().extract().asPrettyString(), false);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown/2")
    @Description("")
    @Epic("Test /api/unknown/2 ")
    public void test5(){
        init("unknown-single");
        stepCheckRequest("/api/unknown/2");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/unknown/2").then().extract().asPrettyString(), false);
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown/23")
    @Description("")
    @Epic("Test /api/unknown/23 ")
    public void test6(){
        init("unknown-single-not-found");
        stepCheckRequest("/api/unknown/23");
        stepCheckResponse(404);
        stepCheckResponseCode(given().when().get("api/unknown/23").then().extract().asString(), false);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users")
    @Description("")
    @Epic("Test /api/users ")
    public void test7(){
        init("post");
        stepCheckRequest("/api/users");
        stepCheckResponse(201);
        People people = People.builder()
                .name("morpheus")
                .job("leader")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(people).when().post("api/users").then().extract().asPrettyString(), true);

    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2 put")
    @Description("")
    @Epic("Test /api/users/2 put")
    public void test8(){
        init("put");
        stepCheckRequest("/api/users/2");
        stepCheckResponse(200);
        People people = People.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(people).when().put("api/users/2").then().extract().asPrettyString(), true);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2 path")
    @Description("")
    @Epic("Test /api/users/2 path")
    public void test9(){
        init("patch");
        stepCheckRequest("/api/users/2");
        People people = People.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(people).when().patch("api/users/2").then().extract().asPrettyString(), true);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2 delete")
    @Description("")
    @Epic("Test /api/users/2 delete")
    public void test10(){
        init("delete");
        stepCheckRequest("/api/users/2");
        stepCheckResponse(204);
        stepCheckResponseCode(given().when().delete("api/users/2").then().extract().asPrettyString(), true);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/register")
    @Description("")
    @Epic("Test /api/register")
    public void test11(){
        init("register-successful");
        stepCheckRequest("/api/register");

        stepCheckRequestCode("{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}");
        stepCheckResponse(200);
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/register").then().extract().asPrettyString(), false);

    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/register")
    @Description("")
    @Epic("Test /api/register")
    public void test12(){
        init("register-unsuccessful");
        stepCheckRequest("/api/register");
        stepCheckRequestCode("{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}");
        stepCheckResponse(400);
        User user = User.builder()
                .email("sydney@fife")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/register").then().extract().asPrettyString(), false);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/login")
    @Description("")
    @Epic("Test /api/login")
    public void test13(){
        init("login-successful");
        stepCheckRequest("/api/login");
        stepCheckRequestCode("{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}");
        stepCheckResponse(200);
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/login").then().extract().asPrettyString(), false);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/login")
    @Description("")
    @Epic("Test /api/login")
    public void test14(){
        init("login-unsuccessful");
        stepCheckRequest("/api/login");
        stepCheckRequestCode("{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}");
        stepCheckResponse(400);
        User user = User.builder()
                .email("peter@klaven")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/login").then().extract().asPrettyString(), false);
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users?delay=3")
    @Description("")
    @Epic("Test /api/users?delay=3")
    public void test15(){
        init("delay", true);
        stepCheckDelay(4);
        stepCheckRequest("/api/users?delay=3");
        stepCheckResponse(200);

        stepCheckResponseCode(given().when().get("api/users?delay=3").then().extract().asPrettyString(), false);
    }


}
