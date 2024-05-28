package org.example.tests;

import io.qameta.allure.*;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.driver.TestListener;
import org.example.pageTemplate.OnlineTradePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Тесты сайта onlinetrade.ru")
public class OnlineTradeTest extends DriverSetup {
    public static OnlineTradePage onlineTradePage;
    private static final Logger logger = LoggerFactory.getLogger(OnlineTradeTest.class);

    @BeforeEach
    @Step("Инициализация страницы")
    public void init() {
        logger.info("Start test page onlinetrade");
        onlineTradePage = new OnlineTradePage(driver);

        driver.get(ConfProperties.getProperties("onlinetrade"));
        logger.info("Page get success");
    }

    @Step("Вывод первых 5 элементов")
    public String stepLogProduct() {
        String elementName = null;
        logger.info("Start log first 5 products");
        for (int i = 0; i < 5; i++) {
            logger.info(onlineTradePage.getNameAndPriceProduct(i));
            if (i == 1) {
                elementName = onlineTradePage.getNameAndPriceProduct(i);
            }
        }
        logger.info("End log first 5 products");
        return elementName;
    }

    @Step("Переход в каталог категории")
    public void stepOpenCatalog() {
        onlineTradePage.clickCatalogButton();
        logger.info("Catalog open");

        onlineTradePage.choiceElectronikaButton();
        logger.info("Element electronika choices");

        onlineTradePage.clickSmartfonyButton();
        logger.info("Smartfony page open");
    }

    @Step("Проверка поиска")
    public void stepCheckSearch() {
        String searchElement = stepLogProduct();

        onlineTradePage.inputSearch(searchElement);
        logger.info("Input search result");

        onlineTradePage.clickSearchButton();
        logger.info("Click search");

        assertTrue(onlineTradePage.getNameAndPriceProduct(0).contains(searchElement));
    }

    @Step("Установка фильтрации")
    public void stepSetFilterApple() {
        onlineTradePage.clickAppleFilterButton();
        logger.info("Apple filter selected");
    }

    @Step("Проверка соответсвия производителя")
    public boolean stepCheckProductMaker() {
        String elementName = null;
        logger.info("Start check product maker contains apple");
        for (int i = 0; i < 5; i++) {
            elementName = onlineTradePage.getNameAndPriceProduct(i);
            boolean check =  elementName.toLowerCase().contains("apple");
            logger.info(elementName + " check: " + check);
            if (!check) return check;

        }
        logger.info("End check product maker contains apple");

        return true;
    }

    @Step("Применение сортировки")
    public void stepSort(){
        onlineTradePage.clickSortButton();
        logger.info("Sort success");
    }

    @Step("Проверка на корректность сортировки")
    public void stepCheckSort(){
        List<Integer> listProductPrice = onlineTradePage.getPriceProducts(10);
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

    @Test
    @Link(name = "onlinetrade", url = "https://www.onlinetrade.ru")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра работы поиска сайта onlinetrade")
    @Description("")
    @Epic("Test for site https://www.onlinetrade.ru")
    public void test1() {
        stepOpenCatalog();
        stepSetFilterApple();
        stepCheckSearch();
    }

    @Test
    @Link(name = "onlinetrade", url = "https://www.onlinetrade.ru")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра работы фильтрации по производителю на сайте onlinetrade")
    @Description("")
    @Epic("Test for site https://www.onlinetrade.ru")
    public void test2() {
        stepOpenCatalog();
        stepSetFilterApple();
        assertTrue(stepCheckProductMaker());

    }

    @Test
    @Link(name = "onlinetrade", url = "https://www.onlinetrade.ru")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра работы фильтрации по цене на сайте onlinetrade")
    @Description("")
    @Epic("Test for site https://www.onlinetrade.ru")
    public void test3() {
        stepOpenCatalog();
        stepLogProduct();
        stepSort();
        stepCheckSort();
    }
}
