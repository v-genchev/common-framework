package pageobjects;

import drivermanager.DynamicDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Component {

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

    public void selectByVisibleText(WebElement selectElement, String text) {
        Select select = new Select(selectElement);
        select.selectByVisibleText(text);
    }

    public List<String> getElementsText(List<WebElement> elements) {
        List<String> elementsText = new ArrayList<>();
        for (WebElement element : elements) {
            elementsText.add(element.getText());
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

    public void typeWithClear(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public void scrollToView(WebElement element){
        scrollToView(element, false);
    }

    public void scrollToView(WebElement element, Boolean allignToTop) {
        ((JavascriptExecutor) driver).executeScript(String.format("arguments[0].scrollIntoView(%s);", allignToTop), element);
    }

    public void hoverOverElement(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }

    public void waitForElementToDissapear(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, waitIntervalElement);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public String getElementNodeText(WebElement element){
        String elementText = element.getText();
        List<WebElement> children = element.findElements(By.xpath("./*"));
        for (WebElement child: children){
            elementText = elementText.replaceFirst(child.getText(),"");
        }
        return elementText.trim();
    }



}
