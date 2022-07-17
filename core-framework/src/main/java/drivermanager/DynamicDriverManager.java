package drivermanager;

import helpers.PropertiesCache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;


public class  DynamicDriverManager implements DriverManager {

    private final DriverManager driverManager;
    private String currentWindow;
    private Set<String> windowHandles;
    private final int waitIntervalNewWindow = 5;

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    public DynamicDriverManager() {
        String browserType = PropertiesCache.getInstance().getProperty("browser");
        switch (browserType.toLowerCase()) {
            case "firefox":
                driverManager = new FirefoxDriverManager();
                break;
            case "chrome":
                driverManager = new ChromeDriverManager();
                break;
            default:
                throw new RuntimeException("Unsupported browser");
        }
    }
    @Override
    public void createDriver() {
        String executionType = PropertiesCache.getInstance().getProperty("env.execution");
        if (executionType.equals("local")){
            driverManager.createDriver();
        }
        else{
            try{
                URL remoteUrl = new URL(PropertiesCache.getInstance().getProperty("remote.driver.url"));
                RemoteWebDriver remoteWebDriver = new RemoteWebDriver(remoteUrl, getOptions());
                driverManager.setDriver(remoteWebDriver);
            }
            catch (MalformedURLException e){
                logger.error("Url provided in config.properties is malformed " + e);
            }
        }
        currentWindow = getCurrentWindowHandle();
        windowHandles = getWindowHandles();
    }

    @Override
    public WebDriver getDriver() {
        return driverManager.getDriver();
    }

    @Override
    public Capabilities getOptions() {
        return driverManager.getOptions();
    }

    @Override
    public void setDriver(WebDriver driver) {
        driverManager.setDriver(driver);
    }

    @Override
    public void quitDriver() {
        driverManager.quitDriver();
    }

    @Override
    public void switchToWindow(String window){
        driverManager.switchToWindow(window);
        currentWindow = getCurrentWindowHandle();
    }

    public void switchToNewWindow(){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitIntervalNewWindow));
        wait.until(ExpectedConditions.numberOfWindowsToBe(windowHandles.size() + 1));
        windowHandles = driverManager.getWindowHandles();
        for (String windowHandle : windowHandles){
            if(!currentWindow.contentEquals(windowHandle)){
                switchToWindow(windowHandle);
                break;
            }
        }
    }
}
