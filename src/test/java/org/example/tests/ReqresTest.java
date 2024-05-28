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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Тесты сайта reqres")
@Disabled
public class ReqresTest extends DriverSetup {
    public static ReqresPage reqresPage;
    private static final Logger logger = LoggerFactory.getLogger(ReqresTest.class);

    @Step("Инициализация страницы")
    public void init(String name){
        logger.info("Start test page reqres endpoint " + name);
        reqresPage = new ReqresPage(driver);
        driver.get(ConfProperties.getProperties("reqres"));
        logger.info("Page get success");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users?page=2")
    @Description("")
    @Epic("Test /api/users?page=2 ")
    public void test1(){
        String endpoint = "/api/users?page=2";
        init(endpoint);
        reqresPage.setEndPoint(endpoint);
        logger.info("Select endpoint");

        assertEquals(endpoint, reqresPage.getLabelRequest());
        logger.info("Checked request");
        assertEquals(200, Integer.parseInt(reqresPage.getLabelResponse()));
        logger.info("Checked response");
        assertEquals("""
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
                }""", reqresPage.getLabelResponseCode());
        logger.info("Checked response code");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/2")
    @Description("")
    @Epic("Test /api/users/2 ")
    public void test2(){
        String endpoint = "/api/users/2";
        init(endpoint);
        reqresPage.setEndPoint(endpoint);
        logger.info("Select endpoint");

        assertEquals(endpoint, reqresPage.getLabelRequest());
        logger.info("Checked request");
        assertEquals(200, Integer.parseInt(reqresPage.getLabelResponse()));
        logger.info("Checked response");
        assertEquals("{\n" +
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
                "}", reqresPage.getLabelResponseCode());
        logger.info("Checked response code");
    }


    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/users/23")
    @Description("")
    @Epic("Test /api/users/23 ")
    public void test3(){
        String endpoint = "/api/users/23";
        init(endpoint);
        reqresPage.setEndPoint(endpoint);
        logger.info("Select endpoint");

        assertEquals(endpoint, reqresPage.getLabelRequest());
        logger.info("Checked request");
        assertEquals(404, Integer.parseInt(reqresPage.getLabelResponse()));
        logger.info("Checked response");
        assertEquals("{}", reqresPage.getLabelResponseCode());
        logger.info("Checked response code");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown")
    @Description("")
    @Epic("Test /api/unknown ")
    public void test4(){
        String endpoint = "/api/unknown";
        init(endpoint);
        reqresPage.setEndPoint(endpoint);
        logger.info("Select endpoint");

        assertEquals(endpoint, reqresPage.getLabelRequest());
        logger.info("Checked request");
        assertEquals(200, Integer.parseInt(reqresPage.getLabelResponse()));
        logger.info("Checked response");
        assertEquals("{\n" +
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
                "}", reqresPage.getLabelResponseCode());
        logger.info("Checked response code");
    }

    @Test
    @Link(name = "Reqres", url = "https://reqres.in/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра эндпоинта /api/unknown/2")
    @Description("")
    @Epic("Test /api/unknown/2 ")
    public void test5(){
        String endpoint = "/api/unknown/2";
        init(endpoint);
        reqresPage.setEndPoint(endpoint);
        logger.info("Select endpoint");

        assertEquals(endpoint, reqresPage.getLabelRequest());
        logger.info("Checked request");
        assertEquals(200, Integer.parseInt(reqresPage.getLabelResponse()));
        logger.info("Checked response");
        assertEquals("{\n" +
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
                "}", reqresPage.getLabelResponseCode());
        logger.info("Checked response code");
    }

}
