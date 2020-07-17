
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAutomation extends TestBase {


    @Test
    public void testing() {
        driver.navigate().to("https://editest.moizakaz.biz:1993/Home/Index");
        WebDriverWait explicitWait = new WebDriverWait(driver, 10);

//        driver.findElement(By.xpath("//*[@id='user']")).sendKeys("test_gamma55");
        driver.findElement(By.cssSelector("#user")).sendKeys("test_gamma55");
        driver.findElement(By.cssSelector("#password")).sendKeys("976oVB>hD:}Br?");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row']//button"))).click();
        //driver.findElement(By.xpath("//button")).click();


        Actions actions = new Actions(driver);


        //WebElement grid = driver.findElement(By.xpath("//div[@class='all']/div/div[@role='grid']"));

//        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='all']/div/div[@role='grid']//table[@id='list']")));

        List<WebElement> rows = driver.findElements(By.xpath("//tr[@role='row' and @tabindex='-1']"));
        System.out.println(rows.size());

        WebElement zakazID = rows.get(0).findElement(By.xpath("//*[@aria-describedby='list_id']"));
        actions.doubleClick(zakazID).build().perform();
//
//        actions.doubleClick(zakazID);
//        assertThat(res).isEqualTo("Online Store | My Store");
    }
}