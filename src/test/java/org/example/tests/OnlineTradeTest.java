package org.example.tests;

import io.qameta.allure.*;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.driver.TestListener;
import org.example.pageTemplate.LambdaPage;
import org.example.pageTemplate.OnlineTradePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Тесты сайта onlinetrade.ru")
public class OnlineTradeTest extends DriverSetup {
    public static OnlineTradePage onlineTradePage;
    private static final Logger logger = LoggerFactory.getLogger(OnlineTradeTest.class);


    @Step("Вывод первых 5 элементов")
    public String logProduct(){
        String elementName = null;
        logger.info("Start log first 5 products");
        for (int i = 0; i < 5; i++) {
            logger.info(onlineTradePage.getNameAndPriceProduct(i));
            if (i == 1){
                elementName = onlineTradePage.getNameAndPriceProduct(i);
            }
        }
        logger.info("End log first 5 products");
        return elementName;
    }

    @Test
    @Link(name = "onlinetrade", url = "https://www.onlinetrade.ru")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра работы сайта onlinetrade")
    @Description("")
    @Epic("Test for site https://www.onlinetrade.ru")
    public void test(){
        logger.info("Start test page onlinetrade");
        onlineTradePage = new OnlineTradePage(driver);

        driver.get(ConfProperties.getProperties("onlinetrade"));
        logger.info("Page get success");

        onlineTradePage.clickCatalogButton();
        logger.info("Catalog open");

        onlineTradePage.choiceElectronikaButton();
        logger.info("Element electronika choices");

        onlineTradePage.clickSmartfonyButton();
        logger.info("Smartfony page open");

        onlineTradePage.clickAppleFilterButton();
        logger.info("Apple filter selected");

        String searchElement = logProduct();

        onlineTradePage.inputSearch(searchElement);
        logger.info("Input search result");

        onlineTradePage.clickSearchButton();
        logger.info("Click search");

        assertEquals(searchElement, onlineTradePage.getNameAndPriceProduct(0));

    }


}
