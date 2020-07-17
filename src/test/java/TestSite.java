import org.openqa.selenium.*;
import org.testng.annotations.Test;

import java.util.*;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class TestSite extends TestBase {

    @Test
    public void asideCheckLinks() {
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        List<WebElement> aside = driver.findElements(By.cssSelector("#app-"));

//        WebElement appearance = driver.findElement(By.cssSelector("#app- > a[href *= appearance]"));
//        appearance.click();
//        assertThat(driver.getTitle()).isNotNull();
//
//        WebElement catalog = driver.findElement(By.cssSelector("#app- > a[href *= catalog]"));
//        catalog.click();
//        assertThat(driver.getTitle()).isNotNull();

        for (int i = 1; i <= aside.size(); i++) {
            WebElement elem = driver.findElement(By.cssSelector("#app-:nth-of-type(" + i + ")"));
            elem.click();
            assertThat(driver.getTitle()).isNotNull();
        }

    }

    @Test
    public void imgCheckStickers() {
        List<String> blocks = new ArrayList<String>();
        blocks.add("box-most-popular");
        blocks.add("box-latest-products");
        blocks.add("box-campaigns");

        driver.navigate().to("http://localhost/litecart/");
        for (int k = 0; k < blocks.size(); k++) {
            String block = blocks.get(k);
            List<WebElement> imgs = driver.findElements(By.cssSelector("div#" + block + " .image-wrapper"));
            System.out.println("Block: " + block);
            System.out.println(imgs.size());
            for (int i = 0; i < imgs.size(); i++) {
                List<WebElement> tagsInImgWrp = imgs.get(i).findElements(By.cssSelector("div"));
                boolean g = elementIsPresent(driver, By.cssSelector("div"));
                System.out.println("Quantity of elements inside Image-Wrapper: " + tagsInImgWrp.size());
                System.out.println("Element is present: " + elementIsPresent(driver, By.cssSelector("div")));
                assertThat(tagsInImgWrp.size()).isEqualTo(1);

                if (!g) {

                }

                /**
                 * Сделать проверку на несуществующее количество тегов внутри ( > 2 ) и поиск по <div name></div>
                 */
            }
        }

//        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
//        Long d = (Long) javascriptExecutor.executeScript("if ( $('div#box-most-popular .image-wrapper').children().length > 0) {return $('div#box-most-popular .image-wrapper').children().length}");
//        System.out.println("d: " + d);
    }

    @Test
    public void imgCheck() {
        List<String> blocks = new ArrayList<String>();
        blocks.add("box-most-popular");
        blocks.add("box-latest-products");
        blocks.add("box-campaigns");

        driver.navigate().to("http://localhost/litecart/");
        for (int k = 0; k < blocks.size(); k++) {
            String block = blocks.get(k);
            List<WebElement> aLink = driver.findElements(By.cssSelector("#" + block + " a.link"));

            System.out.println("Block: " + block);
            for (int i = 0 ; i < aLink.size(); i++ ) {

                boolean el = elementIsPresent(aLink.get(i), By.cssSelector(".image-wrapper div"));


                if (!el) {
                    WebElement name = aLink.get(i).findElement(By.cssSelector(".name"));
                    System.out.println("Block: " + block + ". Name of product: " + name.getText());
                }
            }
        }
    }

    @Test
    public void alphabeticalOrder() {
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        WebElement countries = driver.findElement(By.xpath("//*[@id='app-']/a[.//span[text()='Countries']]"));
        countries.click();

        List<WebElement> countryNames = driver.findElements(By.cssSelector(".dataTable tr.row td:nth-of-type(5)"));
        List<String> notSortedNames = new ArrayList<String>();
        List<String> sortedNames = new ArrayList<String>();
        for (WebElement el : countryNames) {
            notSortedNames.add(el.getText());
        }

        for (String s : notSortedNames) {
            sortedNames.add(s);
        }

        Collections.sort(sortedNames);

        assertThat(sortedNames).isEqualTo(notSortedNames);
    }

    @Test
    public void notZeroZoneAlphabeticalOrder() {
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        WebElement countries = driver.findElement(By.xpath("//*[@id='app-']/a[.//span[text()='Countries']]"));
        countries.click();

        List<WebElement> rows = driver.findElements(By.cssSelector(".dataTable tr.row"));

        List<String> namesGeozones = new ArrayList<String>();

        for (WebElement el : rows) {
            WebElement zone = el.findElement(By.cssSelector("td:nth-of-type(6)"));
            if (!zone.getText().equals("0")) {
                namesGeozones.add(el.findElement(By.cssSelector("td:nth-of-type(5)")).getText());
            }
        }

//        WebElement countryWithGeozone = driver.findElement(By.xpath("//table[@class='dataTable']//a[text()='Canada']"));
//        countryWithGeozone.click();


        for (String s : namesGeozones) {
            WebElement countryWithGeozone = driver.findElement(By.xpath("//table[@class='dataTable']//a[text()='" + s + "']"));
            countryWithGeozone.click();
            List<WebElement> geozonesInsideCountry = driver.findElements(By.xpath("//table[@id='table-zones']//tr[position() >= 1 and position() < last()]/td[3]"));

            List<String> notSortedNames = new ArrayList<String>();

            for (WebElement el : geozonesInsideCountry) {
                notSortedNames.add(el.getText());
            }

            List<String> sortedNames = new ArrayList<String>(notSortedNames);
            Collections.sort(sortedNames);

            System.out.println(sortedNames.size() + " " + notSortedNames.size());

            assertThat(sortedNames).isEqualTo(notSortedNames);

            driver.navigate().back();

        }
    }

    @Test
    public void zoneAlphabeticalOrder() {
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        WebElement geoZones = driver.findElement(By.xpath("//*[@id='app-']/a[.//span[text()='Geo Zones']]"));
        geoZones.click();

        List<WebElement> names = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']/td[3][.//a]"));

        List<String> namesGeozones = new ArrayList<String>();

        for (WebElement el : names) {
            namesGeozones.add(el.getText());
        }


        for (String s : namesGeozones) {
            WebElement geozone = driver.findElement(By.xpath("//table[@class='dataTable']//a[text()='" + s + "']"));
            geozone.click();
            List<String> notSortedNames = new ArrayList<String>();
            List<WebElement> geozones = driver.findElements(By.xpath("//table[@id='table-zones']//td[3]//option[@selected='selected']"));
            for (WebElement element : geozones) {
                notSortedNames.add(element.getText());
            }
            List<String> sortedNames = new ArrayList<String>(notSortedNames);
            Collections.sort(sortedNames);
            System.out.println(sortedNames.size() + " " + notSortedNames.size());
            assertThat(sortedNames).isEqualTo(notSortedNames);
            driver.navigate().back();
        }

    }

    @Test
    public void checkProduct() {
        driver.navigate().to("http://localhost/litecart/");
        WebElement campaignFirstProduct = driver.findElement(By.cssSelector("#box-campaigns li"));
        String title = campaignFirstProduct.findElement(By.cssSelector("a.link")).getAttribute("title");
        campaignFirstProduct.click();
        WebElement productTitleOnProductPage = driver.findElement(By.cssSelector("#box-product h1"));
        String titleOnProductPage = productTitleOnProductPage.getText();
        assertThat(title).isEqualTo(titleOnProductPage);
    }

    @Test
    public void checkProductPrices() {
        driver.navigate().to("http://localhost/litecart/");
        WebElement campaignFirstProduct = driver.findElement(By.cssSelector("#box-campaigns li"));
        List<WebElement> prices = campaignFirstProduct.findElements(By.cssSelector(".price-wrapper *"));
        String priceWithoutTax =  prices.get(0).getText();
        String priceWithTax =  prices.get(1).getText();

        String lineAttValue = prices.get(0).getAttribute("color");
        String color = prices.get(0).getCssValue("text-decoration-line");
        System.out.println(lineAttValue + " " + color);
        String title = campaignFirstProduct.findElement(By.cssSelector("a.link")).getAttribute("title");
        campaignFirstProduct.click();
        WebElement productTitleOnProductPage = driver.findElement(By.cssSelector("#box-product h1"));
        String titleOnProductPage = productTitleOnProductPage.getText();
        assertThat(title).isEqualTo(titleOnProductPage);

        List<WebElement> pricesOnProductPage = driver.findElements(By.cssSelector("#box-product .price-wrapper *"));
        String priceWithoutTaxOnPage = pricesOnProductPage.get(0).getText();
        String priceWithTaxOnPage = pricesOnProductPage.get(1).getText();
        String lineAttValueOnPage = pricesOnProductPage.get(0).getCssValue("color");
        String colorOnPage = pricesOnProductPage.get(0).getCssValue("text-decoration-line");

        System.out.println("priceWithoutTax " + priceWithoutTax);
        System.out.println("priceWithTax " + priceWithTax);
        System.out.println("priceWithoutTaxOnPage " + priceWithoutTaxOnPage);
        System.out.println("priceWithTaxOnPage " + priceWithTaxOnPage);

        assertThat(priceWithoutTax).isEqualTo(priceWithoutTaxOnPage);
        assertThat(priceWithTax).isEqualTo(priceWithTaxOnPage);
        assertThat(lineAttValue).isEqualTo(lineAttValueOnPage);
        assertThat(color).isEqualTo(colorOnPage);
    }


    public boolean elementIsPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean elementIsPresent(WebElement element, By locator) {
        try {
            element.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}

