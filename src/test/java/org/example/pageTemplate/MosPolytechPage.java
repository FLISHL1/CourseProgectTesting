package org.example.pageTemplate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class MosPolytechPage {
    public WebDriver driver;

    @FindBy(xpath = "//a[@title='Расписание']")
    private WebElement linkSchedule;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/']")
    private WebElement linkSchedulePageGroup;



    public MosPolytechPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickLinkSchedule(){
        linkSchedule.click();
    }
    public void clickLinkSchedulePageGroup(){
        String originalWindow = driver.getWindowHandle();
        linkSchedulePageGroup.click();

        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }




}
