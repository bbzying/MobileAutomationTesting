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
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumHelper extends BaseObject{
    private static final Logger logger = LogManager.getLogger(AppiumHelper.class);
    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;

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


    public static void initDriver() throws MalformedURLException {
        String deviceName = props.getProperty("deviceName");
        String appPath = props.getProperty("appPath");
        String appiumIPAddress = props.getProperty("appiumIPAddress");
        int appiumPort = Integer.parseInt(props.getProperty("appiumPort"));
        String url = "http://" + appiumIPAddress + ":" + appiumPort + "/";
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setCapability("enforceXPath1", true);
        options.setApp(appPath);
        driver = new AndroidDriver(new URL(url), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void quitDriver(){
        driver.quit();
    }

    public static void stopAppium(){
        logger.info("Close Appium service");
        service.stop();
    }

    public WebElement findElement(By locator){
        logger.info("Locate element " + locator.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void scrollTextIntoView(String text){
        logger.info("Scroll text into view : " + text);
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
    }

    public void clickOnElementWithText(String text) {
        logger.info("Click on element with text : " + text);
        scrollTextIntoView(text);
        String xpath = "//*[@text='"+ text+"']";
        WebElement element = findElement(By.xpath(xpath));
        element.click();
    }

    public void populateCheckboxWith(String name, String value) {
        logger.info("Select checkbox '" + name + "' : " + value);
        String xpath = "//android.widget.TextView[@text='"+ name + "']/following::android.widget.CheckBox";
        WebElement element = findElement(By.xpath(xpath));
        if (element.isSelected() ^ Boolean.parseBoolean(value)){
            element.click();
        }
    }

    public void populateFieldWith(String name, String value){
        logger.info("Populate field '" + name + "' with : " + value);
        String xpath = "//*[@text='"+name+"']/following::android.widget.EditText";
        WebElement element = findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    public void clickOnButton(String name){
        logger.info("Click on button : " + name);
        String xpath = "//android.widget.Button[@text='"+ name + "']";
        WebElement element = findElement(By.xpath(xpath));
        element.click();
    }

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
