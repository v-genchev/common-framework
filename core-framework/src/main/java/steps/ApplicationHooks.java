package steps;

import drivermanager.DynamicDriverManager;
import helpers.PropertiesCache;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

public class ApplicationHooks {

    public DynamicDriverManager driverManger;

    public ApplicationHooks(DynamicDriverManager driverManager) {
        this.driverManger = driverManager;
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            File screenshot = ((TakesScreenshot) driverManger.getDriver()).getScreenshotAs(OutputType.FILE);
            try {
                String screenshot_path = String.format("%s/%s.png",
                        PropertiesCache.getInstance().getProperty("test.workspace"), scenario.getName());
                FileUtils.copyFile(screenshot, new File(screenshot_path));
            } catch (Exception ignore) {
            }

            driverManger.quitDriver();
        }
    }
}
