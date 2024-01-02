package helper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class AppiumHelper extends BaseObject{
    private static final Logger logger = LogManager.getLogger(AppiumHelper.class);
    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;

    /**
     * Start Åppium server
     */
    public static void startAppium() {
        logger.info("Start Appium service and connect to mobile APP");
        String jsPath = props.getProperty("appiumJSPath");
        String appiumIPAddress = props.getProperty("appiumIPAddress");
        int appiumPort = Integer.parseInt(props.getProperty("appiumPort"));
        service = new AppiumServiceBuilder().withAppiumJS(new File(jsPath))
                .withIPAddress(appiumIPAddress)
                .usingPort(appiumPort).build();
        service.start();
    }

    /**
     * Initialize mobile driver
     */
    public static void initDriver() {
        String deviceName = props.getProperty("deviceName");
        String appPath = props.getProperty("appPath");
        String appiumIPAddress = props.getProperty("appiumIPAddress");
        int appiumPort = Integer.parseInt(props.getProperty("appiumPort"));
        String url = "http://" + appiumIPAddress + ":" + appiumPort + "/";
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setCapability("enforceXPath1", true);
        options.setApp(appPath);
        try {
            driver = new AndroidDriver(new URL(url), options);
        } catch (Exception e){
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * Quit mobile driver
     */
    public static void quitDriver(){
        driver.quit();
    }

    /**
     * Stop Appium server
     */
    public static void stopAppium(){
        logger.info("Close Appium service");
        service.stop();
    }

    /**
     * Find element by locator with explicitly wait 10 seconds.
     * @param locator element locator
     * @return WebElement
     */
    public WebElement findElement(By locator){
        logger.info("Locate element " + locator.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Scroll element with text into view
     * @param text text to be visible in view
     */
    public void scrollTextIntoView(String text){
        logger.info("Scroll text into view : " + text);
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
    }

    /**
     * Click on element with text on it
     * @param text visible text
     */
    public void clickOnElementWithText(String text) {
        logger.info("Click on element with text : " + text);
        scrollTextIntoView(text);
        String xpath = "//*[@text='"+ text+"']";
        WebElement element = findElement(By.xpath(xpath));
        element.click();
    }

    /**
     * Select or deselect checkbox
     * @param name checkbox name
     * @param value true / false
     */
    public void populateCheckboxWith(String name, String value) {
        logger.info("Select checkbox '" + name + "' : " + value);
        String xpath = "//android.widget.TextView[@text='"+ name + "']/following::android.widget.CheckBox";
        WebElement element = findElement(By.xpath(xpath));
        if (element.isSelected() ^ Boolean.parseBoolean(value)){
            element.click();
        }
    }

    /**
     * Populate field with value
     * @param name field name
     * @param value value need to be input
     */
    public void populateFieldWith(String name, String value){
        logger.info("Populate field '" + name + "' with : " + value);
        String xpath = "//*[@text='"+name+"']/following::android.widget.EditText";
        WebElement element = findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    /**
     * Click on button
     * @param name button name (label)
     */
    public void clickOnButton(String name){
        logger.info("Click on button : " + name);
        String xpath = "//android.widget.Button[@text='"+ name + "']";
        WebElement element = findElement(By.xpath(xpath));
        element.click();
    }

    /**
     * Verify text is present or not
     * @param text visible text
     * @return true / false
     */
    public boolean verifyTextIsPresent(String text){
        logger.info("Verify text '" + text + "' is present or not");
        String xpath = "//*[@text='"+ text+"']";
        try {
            findElement(By.xpath(xpath));
        } catch (TimeoutException e){
            return false;
        }
        return true;
    }

}
