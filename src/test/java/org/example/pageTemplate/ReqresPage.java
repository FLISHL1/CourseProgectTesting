package org.example.pageTemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ReqresPage {
    public WebDriver driver;
    public ReqresPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }


    @FindBy(xpath = "//div[@data-key='endpoints']/ul")
    private WebElement listEndPoint;

    @FindBy(xpath = "//span[@data-key='url']")
    private WebElement labelRequest;

    @FindBy(xpath = "//div[@class='request']//pre[@data-key='output-request']")
    private WebElement labelRequestCode;

    @FindBy(xpath = "//span[@data-key='response-code']")
    private WebElement labelResponse;

    @FindBy(xpath = "//div[@class='response']//pre[@data-key='output-response']")
    private WebElement labelResponseCode;


    public void setEndPoint(String endPointId){
        listEndPoint.findElement(By.xpath("//li[@data-id='"+ endPointId +"']")).click();
    }

    public String getLabelRequestCode(){
        return labelRequestCode.getText();
    }
    public String getLabelRequest(){
        return labelRequest.getText();
    }

    public String getLabelResponse(){
        return labelResponse.getText();
    }

    public String getLabelResponseCode(){
        return labelResponseCode.getText();
    }

    public boolean checkVisibleResponseCode(Integer delay){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(delay));
        wait.until(visibilityOfElementLocated(By.xpath("//div[@class='response']//pre[@data-key='output-response']")));
        return true;
    }

}
