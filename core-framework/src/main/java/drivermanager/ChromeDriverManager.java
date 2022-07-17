package drivermanager;

import helpers.PropertiesCache;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverManager implements DriverManager {

    private WebDriver driver;

    public void createDriver() {
        System.setProperty("webdriver.chrome.driver", PropertiesCache.getInstance().getProperty("chrome.driver.path"));
        driver = new ChromeDriver(getOptions());
    }

    public ChromeOptions getOptions(){
        boolean headless = Boolean.parseBoolean(PropertiesCache.getInstance().getProperty("headless"));

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(headless);
        chromeOptions.addArguments("window-size=1920,1080");
        return chromeOptions;
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
