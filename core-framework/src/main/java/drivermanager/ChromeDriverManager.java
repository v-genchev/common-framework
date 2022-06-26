package drivermanager;

import helpers.PropertiesCache;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverManager implements DriverManager {

    private WebDriver driver;

    public void createDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", PropertiesCache.getInstance().getProperty("chrome.driver.path"));
        driver = new ChromeDriver(chromeOptions);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }
}
