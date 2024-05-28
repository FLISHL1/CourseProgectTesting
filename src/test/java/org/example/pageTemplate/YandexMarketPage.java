package org.example.pageTemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class YandexMarketPage {
    private final WebDriver driver;
    public YandexMarketPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//        wait.until(visibilityOfElementLocated(By.xpath("//input[@class='groups']")));
    }
//
    @FindBy(xpath = "//div[@data-baobab-name='catalog']/button")
    private WebElement buttonCatalog;
    @FindBy(xpath = "//li[@data-zone-data='{\"id\":\"97009164\"}']")
    private WebElement catalogElementComputer;

    @FindBy(xpath = "//div[@data-node-id='f4qz3az']/a")
    private WebElement catalogElementDisk;


    @FindBy(xpath = "//div[@data-apiary-widget-name='@light/Organic']")
    private List<WebElement> catalog;

    @FindBy(xpath = "//button[text()='подешевле']")
    private WebElement sortButton;

    public void clickCatalog(){
        buttonCatalog.click();
    }

    public void clickCatalogElementComputer(){
        Actions action = new Actions(driver);
        action.moveToElement(catalogElementComputer).build().perform();
    }

    public void clickCatalogElementDisk(){
        catalogElementDisk.click();
    }

    public List<Map<String, String>> getProductListFirst(Integer count){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < count; i++) {
            WebElement product = catalog.get(i);
            WebElement name = product.findElement(By.xpath(".//h3[@data-auto='snippet-title']"));
            WebElement price = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]"));

            HashMap<String, String> item = new HashMap<>();
            item.put("name", name.getText());
            item.put("price", price.getText());
            list.add(item);
        }
        return list;
    }

    public void clickSort(){
        sortButton.click();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Integer> getProductPriceListFirst(Integer count){
        List<Integer> list = new ArrayList<>();
        catalog = driver.findElements(By.xpath("//div[@data-apiary-widget-name='@light/Organic']"));

        for (int i = 0; i < count; i++){
            WebElement product = catalog.get(i);
            list.add(Integer.parseInt(product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText().replaceAll(" ", "")));
        }
        return list;
    }
}
