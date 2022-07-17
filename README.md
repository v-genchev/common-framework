### Common automation framework

Framework is intended to be used as a submodule and provides common functionalities that are needed for UI automation with Selenium and Cucumber

---

## Installation

1. Java 11
2. Project is intended to be used as a submodule to a multi module maven project and relies on you having config.properties file in you project resources folder.
3. Currently, the project supports chromedriver and geckodriver
   - Specify the path to the driver in you projects config.properties - chrome.driver.path or gecko.driver.path
   - Support for other browsers can be added on request, one can contribute by just implementing the DriverManager interface
4. You could also execute your test cases on a Docker container, you would need to update your config.properties accordingly.
   ```properties
      env.execution = remote
      browser = chrome/firefox
      headless = true
      remote.driver.url = depends on set up
   ```
   
## Contributing

Only common parts should be added to this repository.

## Features

1. To run your tests you can simply create a Runner class in your project and extend the Runner.java one
2. Common selenium functionalities are implemented in the Component abstract class. Custom fluent waits are implemented in CustomExpectedConditions.
3. Cucumber PicoContainer is used to provide dependency injection to steps ensuring a single webdriver instance is used throughout all of them.
   - DynamicDriverManager.java needs to be injected in all Components and Steps
4. If you want to execute a scenario with specified cucumber tag
   - Through maven - mvn test -Dcucumber.options="--tags @myCustomTag"
   - You can specify custom tags in config.properties file - cucumber.tags=not @skip and @myCustomTag - all features with these tags will be executed