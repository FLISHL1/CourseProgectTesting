package org.example.pageTemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReqresPage {
    public WebDriver driver;
    public ReqresPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }

    private WebElement endPoint;


    @FindBy(xpath = "//*[@id=\"console\"]/div[1]/ul")
    WebElement listEndPoint;

    @FindBy(xpath = "//*[@id=\"console\"]/div[2]/div[1]/p/strong/a/span")
    WebElement labelRequest;

    @FindBy(xpath = "//*[@id=\"console\"]/div[2]/div[2]/p/strong/span")
    WebElement labelResponse;

    @FindBy(xpath = "//*[@id=\"console\"]/div[2]/div[2]/pre")
    WebElement labelResponseCode;


    public void setEndPoint(String endpoint){
        endPoint =  listEndPoint.findElement(By.xpath(".//a[@href='"+ endpoint +"']"));
        endPoint.click();
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



}
