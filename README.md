**Common automation framework **

Using the project, one can execute UI automation tests using selenium and cucumber.

---

## Installation

1. Install JDK and Maven before running any tests. Select already installed java version in Project SDK drop-down in File -> Project Structure.
2. Download [ChromeDriver](https://chromedriver.chromium.org/) or [geckodriver](https://github.com/mozilla/geckodriver/releases) and specify path to the executable in config.properties. Make sure you are dowloading correct driver version (see your Chrome browser version).

---

## Import project in IntelliJ IDEA

If you are using IntelliJ IDEA or any other IDE you can import the project from pom.xml

1. File -> New -> Project From Existing Sources.. -> pom.xml

---

## Running a test

Tests are written in cucumber .feature files. (test/resources/features).

1. Every feature needs to be in separate file. A feature can have multiple scenarios.
2. Every written [step](https://cucumber.io/docs/gherkin/step-organization/) needs to have a [step definition](https://cucumber.io/docs/cucumber/step-definitions/) in steps package.
3. Pages are organized using [Page Object Model](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) design pattern. Every component/page that can be reused needs to be in a separate class (for now extending base class Page.java)
4. Cucumber [PicoContainer](https://github.com/cucumber/cucumber-jvm/tree/master/picocontainer) is used to provide dependency injection to steps ensuring a single webdriver instance is used throughout all steps - achieved through DynamicDriverManager.java (needs to be injected in all Components and Steps)
5. Runner.java is used to run all specified feature files. In order to create configuration for runner - Right click on Runner -> Create Runner.
6. To execute feature files through maven - execute mvn clean test where pom.xml is located
7. If you want to execute a scenario with specified cucumber tag
    1. Through maven - mvn test -Dcucumber.options="--tags @myCustomTag"
    2. You can specify custom tags in config.properties file - cucumber.tags=not @skip and @myCustomTag