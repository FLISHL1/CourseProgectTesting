package org.example.pageTemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LambdaPage {


    public WebDriver driver;
    public LambdaPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }



    @FindBy(xpath="/html/body/div/div/div/span")
    private WebElement labelRemaining;

    @FindBy(xpath = "//ul")
    private WebElement listItem;

    @FindBy(xpath = "//*[@id='sampletodotext']")
    private WebElement inputNameElement;

    @FindBy(xpath = "//*[@id='addbutton']")
    private WebElement inputAddElement;

    @FindBy(xpath = "//h2")
    private WebElement headText;

    public String getLableRemaining(){
        return labelRemaining.getText();
    }
    public String getHeadText(){
        return headText.getText();
    }

    public Boolean isCrossedOutItem(Integer id){

        WebElement item = listItem.findElements(By.tagName("li")).get(id).findElement(By.tagName("span"));
        return item.getAttribute("class").equals("done-true");

    }

    public void selectItem(Integer id){
        WebElement item = listItem.findElements(By.tagName("li")).get(id).findElement(By.tagName("input"));
        item.click();
    }
    public Integer getSizeList(){
        return listItem.findElements(By.tagName("li")).size();
    }
    public void addNewElement(String name){
        inputNameElement.sendKeys(name);
        inputAddElement.click();
    }
}
