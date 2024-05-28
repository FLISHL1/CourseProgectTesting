package org.example.pageTemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class MosPolytechSchedulePage {
    private final WebDriver driver;

    @FindBy(xpath = "//input[@class='groups']")
    private WebElement inputGroups;


    @FindBy(xpath = "//div[contains(@class, 'found-groups')]")
    private WebElement groupsList;

    @FindBy(xpath = "//div[starts-with(@class,'schedule-day')]")
    private List<WebElement> weekDays;

    public MosPolytechSchedulePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(visibilityOfElementLocated(By.xpath("//input[@class='groups']")));
    }

    public void clearInputGroup(){
        inputGroups.clear();
    }

    public void inputGroup(String group){
        inputGroups.sendKeys(group);
    }

    public boolean checkListGroups(String group){
        List<WebElement> webElements = groupsList.findElements(By.tagName("div"));
        if (webElements.size() == 1 && webElements.get(0).getText().equals(group)) return true;
        else return false;
    }

    public void clickGroup(String group){
        groupsList.findElements(By.tagName("div")).get(0).click();
    }

    public boolean checkSelectedWeekDay(int weekDay){
        return weekDays.get(weekDay-1).getAttribute("class").contains("schedule-day_today");
    }
}
