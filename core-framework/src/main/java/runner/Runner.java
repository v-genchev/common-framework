package runner;

import helpers.PropertiesCache;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {"pretty", "junit:target/cucumber-report/cucumber.xml", "json:target/cucumber-report/cucumber.json"},
        features = {"src/test/resources/features"},
        glue = {"steps"}
)

public class Runner extends AbstractTestNGCucumberTests {

    @BeforeTest
    public static void before() {
        String cucumberTags = PropertiesCache.getInstance().getProperty("cucumber.tags");
        if (cucumberTags != null) {
            System.setProperty("cucumber.filter.tags", cucumberTags);
        }
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}