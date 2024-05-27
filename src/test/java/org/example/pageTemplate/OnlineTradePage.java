package org.example.pageTemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class OnlineTradePage {


    public WebDriver driver;

    public OnlineTradePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }

    @FindBy(xpath = "//a[contains(@class, 'catalog')]")
    private WebElement catalogButton;

    @FindBy(xpath = "//a[contains(@href, 'elektronika')]")
    private WebElement elektronikaButton;


    @FindBy(xpath = "/html/body/div[1]/div/div[3]/div[1]/div/div/div/div[2]/ul/li[1]/div/div[1]/div[1]/div/div[1]/ul/li[1]/a")
//    @FindBy(xpath = "//a[contains(@href, 'smartfony')]")
    private WebElement smartfonyButton;

    @FindBy(xpath = "//a[contains(@title, 'APPLE')]")
    private WebElement appleFilterButton;

    @FindBy(xpath = "//div[@class='indexGoods__item']")
    private List<WebElement> cardsProduct;

    @FindBy(xpath = "//input[@name='query']")
    private WebElement searchInput;

    @FindBy(xpath = "//input[@class='header__search__inputGogogo']")
    private WebElement searchButton;


    public void clickCatalogButton() {
        catalogButton.click();
    }

    public void choiceElectronikaButton() {
        Actions action = new Actions(driver);
        action.moveToElement(elektronikaButton).build().perform();
    }

    public void clickSmartfonyButton() {
        smartfonyButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(visibilityOfElementLocated(By.xpath("//a[contains(@title, 'APPLE')]")));
    }

    public void clickAppleFilterButton() {
        appleFilterButton.click();
        updateCardsProduct();
    }

    private void updateCardsProduct() {
        cardsProduct = driver.findElements(By.xpath("//div[@class='indexGoods__item']"));
    }

    public String getNameAndPriceProduct(int num) {
        WebElement cardProduct = cardsProduct.get(num);
        StringBuilder result = new StringBuilder();
        result.append(cardProduct.findElement(By.xpath(".//a[contains(@class,'item__name')]")).getText());
        result.append(" ");
        result.append(cardProduct.findElement(By.xpath(".//span[starts-with(@class,'price')]")).getText());
        return result.toString();
    }

    public void inputSearch(String searchText) {
        searchInput.sendKeys(searchText);
    }

    public void clickSearchButton() {
        searchButton.click();
        updateCardsProduct();
    }
}

