package pageobjects;

import drivermanager.DynamicDriverManager;
import helpers.CustomExpectedConditions;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Component {

    public static Logger logger = Logger.getLogger(Component.class.getName());
    public DynamicDriverManager driverManager;
    private WebDriver driver;
    private final Duration waitIntervalElement = Duration.ofSeconds(5);
    private final Duration waitIntervalPage = Duration.ofSeconds(10);

    public Component(DynamicDriverManager driverManager) {
        this.driverManager = driverManager;
        driver = driverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void waitForElement(WebElement element) {
        waitForElement(element, waitIntervalElement);
    }

    public void waitForPage(WebElement element) {
        waitForElement(element, waitIntervalPage);
    }

    private void waitForElement(WebElement element, Duration waitIntervalTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalTime);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForAllElements(List<WebElement> elements){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForElementToDissapear(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementTextToBe(WebElement element, String text){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(CustomExpectedConditions.elementTextToBe(element, text));
    }

    public void waitForElementValueToBe(WebElement element, String text){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.attributeToBe(element, "value", text));
    }

    public List<String> getElementsText(List<WebElement> elements) {
        List<String> elementsText = new ArrayList<>();
        for (WebElement element : elements) {
            elementsText.add(element.getText());
        }
        return elementsText;
    }

    public List<String> getElementsValues(List<WebElement> elements) {
        List<String> elementsText = new ArrayList<>();
        for (WebElement element : elements) {
            elementsText.add(element.getAttribute("value"));
        }
        return elementsText;
    }

    public List<String> getElementsNodeText(List<WebElement> elements) {
        List<String> elementsText = new ArrayList<>();
        for (WebElement element : elements) {
            elementsText.add(getElementNodeText(element));
        }
        return elementsText;
    }

    public WebElement getElementByText(List<WebElement> elements, String text) {
        List<String> elementsText = getElementsText(elements);
        try {
            return elements.get(elementsText.indexOf(text));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Cannot find element with text %s in the list %s", text, elementsText));
        }
    }

    public WebElement getElementByPartialText(List<WebElement> elements, String text) {

        for(WebElement element : elements){
            if(StringUtils.containsIgnoreCase(element.getText(), text)){
                return element;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find element containing text %s in the list %s", text, text));
    }

    public String getElementNodeText(WebElement element){
        String elementText = element.getText();
        List<WebElement> children = element.findElements(By.xpath("./*"));
        for (WebElement child: children){
            elementText = elementText.replaceFirst(child.getText(),"");
        }
        return elementText.trim();
    }

    public String getElementTextContent(WebElement element){
        return element.getAttribute("textContent");
    }

    public void typeWithClear(WebElement element, String text) {
        element.clear();
        if(!element.getText().equals("") || !element.getAttribute("value").equals("")){
            element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.BACK_SPACE));
        }

        element.sendKeys(text);
    }

    public void removeFocus(WebElement element){
        element.sendKeys(Keys.TAB);
    }

    public void selectByVisibleText(WebElement selectElement, String text) {
        Select select = new Select(selectElement);
        select.selectByVisibleText(text);
    }

    public void checkboxAction(WebElement checkbox, boolean select){
        boolean isSelected = checkbox.isSelected();
        if (select != isSelected){
            checkbox.click();
        }
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, select));
    }

    public void scrollToView(WebElement element){
        scrollToView(element, false);
    }

    public void scrollToView(WebElement element, Boolean allignToTop) {
        ((JavascriptExecutor) driver).executeScript(String.format("arguments[0].scrollIntoView(%s);", allignToTop), element);
    }

    public void actionsClick(WebElement element, int pauseFor){
        new Actions(driver).moveToElement(element).pause(pauseFor).click().perform();
    }

    public void hoverOverElement(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }



}
