package drivermanager;

import helpers.PropertiesCache;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager implements DriverManager {

    private WebDriver driver;

    @Override
    public void createDriver() {
        System.setProperty("webdriver.gecko.driver", PropertiesCache.getInstance().getProperty("gecko.driver.path"));
        driver = new FirefoxDriver(getOptions());
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public FirefoxOptions getOptions() {
        boolean headless = Boolean.parseBoolean(PropertiesCache.getInstance().getProperty("headless"));

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(headless);
        return firefoxOptions;
    }

    @Override
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
