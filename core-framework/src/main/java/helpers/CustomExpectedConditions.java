package helpers;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomExpectedConditions {

    public static ExpectedCondition<Boolean> elementTextToBe(final WebElement element, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getText();
                    return elementText.equals(text);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("element %s text to be %s", text, element);
            }
        };
    }
}
