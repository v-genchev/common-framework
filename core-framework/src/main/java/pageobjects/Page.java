package pageobjects;

import drivermanager.DynamicDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page extends Component {

    public DynamicDriverManager driverManager;

    public Page(DynamicDriverManager driverManager) {
        super(driverManager);
    }

}
