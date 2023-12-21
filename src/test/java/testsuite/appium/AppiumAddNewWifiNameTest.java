package testsuite.appium;

import helper.Hooks;
import objects.appium.Base;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Test APP Add new Wifi name")
@Tag("smoketest")
public class AppiumAddNewWifiNameTest extends Hooks {
    public Base base = new Base();

    @Test
    @DisplayName("Add new wifi name")
    public void testAddNewWifiName(){
        base.clickOnMenu("Preference");
        base.clickOnMenu("3. Preference dependencies");
        base.populateCheckboxWith("WiFi", "true");
        base.clickOnMenu("WiFi settings");
        base.populateFieldWith("WiFi settings", "Jane Wifi");
        base.clickOnButton("OK");
        base.verifyTextIsPresent("WiFi settings");
    }

}
