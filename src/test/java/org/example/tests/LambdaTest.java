package org.example.tests;

import io.qameta.allure.*;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.pageTemplate.LambdaPage;
import org.example.driver.TestListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Тесты сайта lambda sample app")
public class LambdaTest extends DriverSetup {
    public static LambdaPage lambdaPage;
    private static final Logger logger = LoggerFactory.getLogger(LambdaTest.class);


    @Step("Step checking the list item")
    public void checkItem(Integer itemNum){
        logger.info("Item {} start check", itemNum);
        assertFalse(lambdaPage.isCrossedOutItem(itemNum));
        logger.info("Item not crossed out");

        lambdaPage.selectItem(itemNum);
        logger.info("Item clicked");

        assertTrue(lambdaPage.isCrossedOutItem(itemNum));
        logger.info("Item crossed out");

        assertEquals(lambdaPage.getLableRemaining(), (lambdaPage.getSizeList()-itemNum-1) + " of " + lambdaPage.getSizeList() + " remaining");
        logger.info("Label remaining checked");

        logger.info("Item checked " + itemNum);
    }

    @Test
    @Link(name = "Lambda Sample App", url = "https://lambdatest.github.io/sample-todo-app/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра работы сайта lambda")
    @Description("Переходим на сайт, кликаем по каждому пункту, создаем новый пункт, проверяем его")
    @Epic("Test for site https://lambdatest.github.io/sample-todo-app/ ")
    public void test(){
        logger.info("Start test page lambda sample app");
        lambdaPage = new LambdaPage(driver);

        driver.get(ConfProperties.getProperties("lambdaPage"));
        logger.info("Page get success");

        assertEquals(lambdaPage.getHeadText(), "LambdaTest Sample App");
        logger.info("Text headPage equals LambdaTest Sample App");

        for (int i = 0; i< lambdaPage.getSizeList(); i++) {
           checkItem(i);
        }
        logger.info("All item  checked");

        lambdaPage.addNewElement("Test");
        logger.info("New item created with name 'Test'");

        assertFalse(lambdaPage.isCrossedOutItem(lambdaPage.getSizeList()-1));
        logger.info("Item not crossed out");

        assertEquals(lambdaPage.getLableRemaining(), "1 of " + lambdaPage.getSizeList() + " remaining");
        logger.info("Label remaining checked");

        checkItem(lambdaPage.getSizeList() - 1);
        logger.info("Last item checked");
    }


}
