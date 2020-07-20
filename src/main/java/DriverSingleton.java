import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class DriverSingleton {

    private static WebDriver driver;

    private DriverSingleton() {

    }

    public static WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        return driver;
    }
}
