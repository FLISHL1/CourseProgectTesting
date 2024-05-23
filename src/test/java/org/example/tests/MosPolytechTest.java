package org.example.tests;


import io.qameta.allure.*;
import org.example.driver.ConfProperties;
import org.example.driver.DriverSetup;
import org.example.driver.TestListener;
import org.example.pageTemplate.MosPolytechPage;
import org.example.pageTemplate.MosPolytechSchedulePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(TestListener.class)
@Feature("Тесты сайта Московского политеха")
public class MosPolytechTest extends DriverSetup {

    public static MosPolytechPage mosPolytechPage;
    public static MosPolytechSchedulePage mosPolytechSchedulePage;
    private static final Logger logger = LoggerFactory.getLogger(MosPolytechTest.class);

    @Test
    @Link(name = "MosPolytech", url = "https://mospolytech.ru/")
    @Owner(value = "Конопский Кирилл")
    @DisplayName("Провекра отображения расписания на сайте Московского политеха")
    @Description("Переходим на сайт политеха, кликаем по расписанию, кликаем по расписанию, переходим на страницу расписания, вводим группу, выбираем группу, проверяем подстветку дня недели")
    @Epic("Test for site https://mospolytech.ru/ ")
    public void test(){
        logger.info("Start test page mospolytech");
        mosPolytechPage = new MosPolytechPage(driver);
        driver.get(ConfProperties.getProperties("mospolytech"));
        logger.info("Page get success");

        mosPolytechPage.clickLinkSchedule();
        logger.info("Click button schedule");

        mosPolytechPage.clickLinkSchedulePageGroup();
        logger.info("Click button schedule group");

        mosPolytechSchedulePage = new MosPolytechSchedulePage(driver);
        mosPolytechSchedulePage.inputGroup("221-361");
        logger.info("Input group");

        assertTrue(mosPolytechSchedulePage.checkListGroups("221-361"));
        logger.info("Group list checked");

        mosPolytechSchedulePage.clickGroup("221-361");
        logger.info("Group selected");

        int numDay = getDayWeekNumber();
        if (numDay == 7){
            logger.info("The day is not chosen because it is Sunday");
        }else {
            assertTrue(mosPolytechSchedulePage.checkSelectedWeekDay(numDay));
            logger.info("Check selected week day");
        }
    }

    private int getDayWeekNumber(){
        return LocalDate.now().getDayOfWeek().getValue();
    }
}
