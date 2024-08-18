package utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class WebElementUtils {

    public static void clickOnButton(WebElement element) {
        element.click();
    }

    public static void enterText(WebElement element, String text) {
        element.sendKeys(text);
    }

    public static void selectCategory(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

}
