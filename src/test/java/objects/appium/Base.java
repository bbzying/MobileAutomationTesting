package objects.appium;

import helper.AppiumHelper;
import org.junit.jupiter.api.Assertions;

public class Base {
    AppiumHelper helper = new AppiumHelper();

    public void tearDown() {
        AppiumHelper.quitDriver();
    }
    public void clickOnMenu(String menu){
        helper.clickOnElementWithText(menu);
    }

    public void populateCheckboxWith(String name, String value) {
        helper.populateCheckboxWith(name, value);
    }

    public void waitForSeconds(String seconds){
        helper.waitForSeconds(seconds);
    }

    public void populateFieldWith(String name, String value) {
        helper.populateFieldWith(name, value);
    }

    public void clickOnButton(String name) {
        helper.clickOnButton(name);
    }

    public void verifyTextIsPresent(String text) {
        boolean isPresent = helper.verifyTextIsPresent(text);
        Assertions.assertTrue(isPresent);
    }

    public void verifyTextIsNotPresent(String text) {
        boolean isPresent = helper.verifyTextIsPresent(text);
        Assertions.assertFalse(isPresent);
    }


}
