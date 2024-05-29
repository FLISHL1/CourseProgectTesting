package org.example.tests;

import io.qameta.allure.*;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.driver.TestListener;
import org.example.pageTemplate.ReqresPage;
import org.junit.jupiter.api.Disabled;
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
        assertTrue(reqresPage.getLabelResponseCode().matches(code));
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
        stepCheckResponseCode("""
                {
                    "page": 2,
                    "per_page": 6,
                    "total": 12,
                    "total_pages": 2,
                    "data": [
                        {
                            "id": 7,
                            "email": "michael.lawson@reqres.in",
                            "first_name": "Michael",
                            "last_name": "Lawson",
                            "avatar": "https://reqres.in/img/faces/7-image.jpg"
                        },
                        {
                            "id": 8,
                            "email": "lindsay.ferguson@reqres.in",
                            "first_name": "Lindsay",
                            "last_name": "Ferguson",
                            "avatar": "https://reqres.in/img/faces/8-image.jpg"
                        },
                        {
                            "id": 9,
                            "email": "tobias.funke@reqres.in",
                            "first_name": "Tobias",
                            "last_name": "Funke",
                            "avatar": "https://reqres.in/img/faces/9-image.jpg"
                        },
                        {
                            "id": 10,
                            "email": "byron.fields@reqres.in",
                            "first_name": "Byron",
                            "last_name": "Fields",
                            "avatar": "https://reqres.in/img/faces/10-image.jpg"
                        },
                        {
                            "id": 11,
                            "email": "george.edwards@reqres.in",
                            "first_name": "George",
                            "last_name": "Edwards",
                            "avatar": "https://reqres.in/img/faces/11-image.jpg"
                        },
                        {
                            "id": 12,
                            "email": "rachel.howell@reqres.in",
                            "first_name": "Rachel",
                            "last_name": "Howell",
                            "avatar": "https://reqres.in/img/faces/12-image.jpg"
                        }
                    ],
                    "support": {
                        "url": "https://reqres.in/#support-heading",
                        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
                    }
                }""", false);
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
        stepCheckResponseCode("{\n" +
                "    \"data\": {\n" +
                "        \"id\": 2,\n" +
                "        \"email\": \"janet.weaver@reqres.in\",\n" +
                "        \"first_name\": \"Janet\",\n" +
                "        \"last_name\": \"Weaver\",\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}", false);
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
        stepCheckResponseCode("{}", false);
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
        stepCheckResponseCode("{\n" +
                "    \"page\": 1,\n" +
                "    \"per_page\": 6,\n" +
                "    \"total\": 12,\n" +
                "    \"total_pages\": 2,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"cerulean\",\n" +
                "            \"year\": 2000,\n" +
                "            \"color\": \"#98B2D1\",\n" +
                "            \"pantone_value\": \"15-4020\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"fuchsia rose\",\n" +
                "            \"year\": 2001,\n" +
                "            \"color\": \"#C74375\",\n" +
                "            \"pantone_value\": \"17-2031\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"true red\",\n" +
                "            \"year\": 2002,\n" +
                "            \"color\": \"#BF1932\",\n" +
                "            \"pantone_value\": \"19-1664\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"name\": \"aqua sky\",\n" +
                "            \"year\": 2003,\n" +
                "            \"color\": \"#7BC4C4\",\n" +
                "            \"pantone_value\": \"14-4811\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 5,\n" +
                "            \"name\": \"tigerlily\",\n" +
                "            \"year\": 2004,\n" +
                "            \"color\": \"#E2583E\",\n" +
                "            \"pantone_value\": \"17-1456\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 6,\n" +
                "            \"name\": \"blue turquoise\",\n" +
                "            \"year\": 2005,\n" +
                "            \"color\": \"#53B0AE\",\n" +
                "            \"pantone_value\": \"15-5217\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}", false);
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
        stepCheckResponseCode("{\n" +
                "    \"data\": {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"fuchsia rose\",\n" +
                "        \"year\": 2001,\n" +
                "        \"color\": \"#C74375\",\n" +
                "        \"pantone_value\": \"17-2031\"\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}", false);
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
        stepCheckResponseCode("{}", false);
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

        stepCheckResponseCode("\\{\n" +
                "    \\\"name\\\": \\\"morpheus\\\",\n" +
                "    \\\"job\\\": \\\"leader\\\",\n" +
                "    \\\"id\\\": \\\"\\d*\\\",\n" +
                "    \\\"createdAt\\\": \\\".*\\\"\n" +
                "\\}", true);

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
        stepCheckResponseCode("\\{\n" +
                "    \\\"name\\\": \\\"morpheus\\\",\n" +
                "    \\\"job\\\": \\\"zion resident\\\",\n" +
                "    \\\"updatedAt\\\": \\\".*\\\"\n" +
                "\\}", true);
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
        stepCheckRequestCode("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}");
        stepCheckResponseCode("\\{\n" +
                "    \\\"name\\\": \\\"morpheus\\\",\n" +
                "    \\\"job\\\": \\\"zion resident\\\",\n" +
                "    \\\"updatedAt\\\": \\\".*\\\"\n" +
                "\\}", true);
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
        stepCheckRequestCode("");
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
        stepCheckResponseCode("{\n" +
                "    \"id\": 4,\n" +
                "    \"token\": \"QpwL5tke4Pnpja7X4\"\n" +
                "}", false);
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
        stepCheckResponseCode("{\n" +
                "    \"error\": \"Missing password\"\n" +
                "}", false);
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
        stepCheckResponseCode("{\n" +
                "    \"token\": \"QpwL5tke4Pnpja7X4\"\n" +
                "}", false);
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
        stepCheckResponseCode("{\n" +
                "    \"error\": \"Missing password\"\n" +
                "}", false);
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

        stepCheckResponseCode("{\n" +
                "    \"page\": 1,\n" +
                "    \"per_page\": 6,\n" +
                "    \"total\": 12,\n" +
                "    \"total_pages\": 2,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"email\": \"george.bluth@reqres.in\",\n" +
                "            \"first_name\": \"George\",\n" +
                "            \"last_name\": \"Bluth\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/1-image.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"email\": \"janet.weaver@reqres.in\",\n" +
                "            \"first_name\": \"Janet\",\n" +
                "            \"last_name\": \"Weaver\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"email\": \"emma.wong@reqres.in\",\n" +
                "            \"first_name\": \"Emma\",\n" +
                "            \"last_name\": \"Wong\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/3-image.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"email\": \"eve.holt@reqres.in\",\n" +
                "            \"first_name\": \"Eve\",\n" +
                "            \"last_name\": \"Holt\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/4-image.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 5,\n" +
                "            \"email\": \"charles.morris@reqres.in\",\n" +
                "            \"first_name\": \"Charles\",\n" +
                "            \"last_name\": \"Morris\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/5-image.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 6,\n" +
                "            \"email\": \"tracey.ramos@reqres.in\",\n" +
                "            \"first_name\": \"Tracey\",\n" +
                "            \"last_name\": \"Ramos\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/6-image.jpg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}", false);
    }


}
