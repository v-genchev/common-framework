package drivermanager;

import helpers.PropertiesCache;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class  DynamicDriverManager implements DriverManager {

    private final DriverManager driverManager;
    private String currentWindow;
    private Set<String> windowHandles;
    private final int waitIntervalNewWindow = 5;

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
        driverManager.createDriver();
        currentWindow = getCurrentWindowHandle();
        windowHandles = getWindowHandles();
    }

    @Override
    public WebDriver getDriver() {
        return driverManager.getDriver();
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
        WebDriverWait wait = new WebDriverWait(getDriver(), waitIntervalNewWindow);
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
