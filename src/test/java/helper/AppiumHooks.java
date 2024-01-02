package helper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AppiumHooks {
    /**
     * Start Appium Server. Run before all Appium tests start.
     */
    @BeforeAll
    public static void setupAll(){
        AppiumHelper.startAppium();
    }

    /**
     * Stop Appium Server. Run after all Appium tests finish.
     */
    @AfterAll
    public static void teardownAll(){
        AppiumHelper.stopAppium();
    }

    /**
     * Setup mobile driver. Run before each test.
     */
    @BeforeEach
    public void setupTest() {
        AppiumHelper.initDriver();
    }

    /**
     * Quite mobile driver. Run after each test.
     */
    @AfterEach
    public void tearDownTest(){
        AppiumHelper.quitDriver();
    }
}
