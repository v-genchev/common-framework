package drivermanager;

import helpers.PropertiesCache;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager implements DriverManager {

    private WebDriver driver;

    @Override
    public void createDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", PropertiesCache.getInstance().getProperty("gecko.driver.dir"));
        driver = new FirefoxDriver(firefoxOptions);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }
}
