package org.example.driver;


import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CustomEvent implements WebDriverListener {

    private static Logger logger = LoggerFactory.getLogger(WebDriver.class);

    @Override
    public void beforeClick(WebElement element) {
        StringBuilder message = new StringBuilder("Клик на ").append(element.getText()).append(" (").append(element.getTagName()).append(".").append(element.getAttribute("class")).append(")");
        Allure.step(message.toString());
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        StringBuilder message = new StringBuilder("Ввод ").append(Arrays.toString(keysToSend)).append(" в ").append(element.getTagName()).append(".").append(element.getAttribute("class"));

        Allure.step(message.toString());
    }
}