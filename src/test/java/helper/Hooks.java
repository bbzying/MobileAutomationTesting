package helper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.net.MalformedURLException;

public class Hooks {
    @BeforeAll
    public static void setupAll(){
        AppiumHelper.startAppium();
        new APIHelper();
    }
    @AfterAll
    public static void teardownAll(){
        AppiumHelper.stopAppium();
    }

    @BeforeEach
    public void setupTest() throws MalformedURLException {
        AppiumHelper.initDriver();
    }

    @AfterEach
    public void tearDownTest(){
        AppiumHelper.quitDriver();
    }
}
