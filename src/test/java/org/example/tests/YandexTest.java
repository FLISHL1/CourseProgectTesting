package org.example.tests;


import io.qameta.allure.*;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.driver.TestListener;
import org.example.pageTemplate.YandexMarketPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(TestListener.class)
@Feature("Тесты сайта Яндекс Маркет")
public class YandexTest extends DriverSetup {
    private YandexMarketPage yandexMarketPage;
    private static final Logger logger = LoggerFactory.getLogger(YandexTest.class);

    @Step("Получение каждого товара")
    private void stepLogProduct(){
        List<Map<String, String>> listProduct = yandexMarketPage.getProductListFirst(5);
        logger.info("Get five first product");
        for (int i = 0; i < listProduct.size(); i++) {
            logger.info("Get product " + (i + 1));
            logger.info("Name product " + listProduct.get(i).get("name") + " Price " + listProduct.get(i).get("price"));
        }
        logger.info(listProduct.size() + " product printed");
    }

    @Step("Получение отсортированного списка")
    private void stepSortList(){
        yandexMarketPage.clickSort();
        logger.info("Sorted product");

        stepCheckSort();
    }
    @Step("Проверка сортировки")
    private void stepCheckSort(){
        List<Integer> listProductPrice = yandexMarketPage.getProductPriceListFirst(10);
        logger.info("Get price first 10 product");
        for(int i = 0; i<listProductPrice.size(); i++){
            if (i + 1 >= listProductPrice.size()){
                break;
            }
            assertEquals(listProductPrice.get(i+1), Math.max(listProductPrice.get(i), listProductPrice.get(i+1)));
        }
        logger.info("Sort correctly");
        Allure.step("Проверка сортировкиy");
    }

    @Step("Инициализации страницы")
    private void init(){
        logger.info("Start test page yandex market");
        yandexMarketPage = new YandexMarketPage(driver);
        driver.get(ConfProperties.getProperties("yandexmarket"));
        logger.info("Page get success");
    }

    @Step("Переход к катологу")
    private void stepTransitionToCatologist(){
        yandexMarketPage.clickCatalog();
        logger.info("Pressed catalog");

        yandexMarketPage.clickCatalogElementComputer();
        logger.info("Selected element computer");


        yandexMarketPage.clickCatalogElementDisk();
        logger.info("Clicked Internal hard drives");
    }

    @Test
    @Link(name = "Яндекс Маркет", url = "https://market.yandex.ru")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра отображения яндекс маркета")
    @Description("")
    @Epic("Test for site https://market.yandex.ru ")
    public void test(){
        init();
        stepTransitionToCatologist();
        stepLogProduct();
        stepSortList();
    }
}
