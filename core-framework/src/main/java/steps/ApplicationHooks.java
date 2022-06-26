package steps;

import drivermanager.DynamicDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class ApplicationHooks {

    public DynamicDriverManager driverManger;

    public ApplicationHooks(DynamicDriverManager driverManager){
        this.driverManger = driverManager;
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            driverManger.quitDriver();
        }
    }
}
