package steps;

import drivermanager.DynamicDriverManager;
import io.cucumber.java.en.And;
import org.testng.Assert;

public class CommonSteps {
    public DynamicDriverManager driverManger;

    public CommonSteps(DynamicDriverManager driverManager){
        this.driverManger = driverManager;
    }

    @And("^I start the browser$")
    public void startBrowser() {
        driverManger.createDriver();
        driverManger.maximizeDriver();
    }

    @And("^I stop the browser$")
    public void stopBrowser() {
        driverManger.quitDriver();
    }

    @And("^I verify current url is (.*)$")
    public void verifyCurrentUrl(String url) {
        Assert.assertEquals(driverManger.getDriver().getCurrentUrl(), url, "Current window url is not as expected");
    }
    @And("^I verify page title is (.*)$")
    public void verifyPageTitle(String title) {
        Assert.assertEquals(driverManger.getDriver().getTitle(), title, "Current page title not as expected");
    }

}
