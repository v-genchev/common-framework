package drivermanager;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

/**
 *  Driver manager interface
 */
public interface DriverManager {

    /**
     * Creates WebDriver instance for a specified browser
     */
    void createDriver();

    /**
     * @return the created WebDriver instance
     */
    WebDriver getDriver();

    /**
     * @return the browser capabilities set
     */
    Capabilities getOptions();

    /**
     * Sets the driver
     */
    void setDriver(WebDriver driver);

    /**
     * @return the current Window Handle
     */
    default String getCurrentWindowHandle(){
        return getDriver().getWindowHandle();
    }

    /**
     * Switches to the desired window handle
     */
    default void switchToWindow(String window){
        getDriver().switchTo().window(window);
    }

    /**
     * Switches to the desired  frame
     */
    default void switchToFrame(WebElement frame){getDriver().switchTo().frame(frame);}

    /**
     * Switches to the default content
     */
    default void switchToDefaultContent(){getDriver().switchTo().defaultContent();}

    /**
     * @return Set<String> of all window handles
     */
    default Set<String> getWindowHandles(){ return getDriver().getWindowHandles(); }

    /**
     * Maximizes the browser window
     */
    default void maximizeDriver() {
      getDriver().manage().window().maximize();
    }

    /**
     *  Quits the driver, closing every associated window
     */
    default void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

}
