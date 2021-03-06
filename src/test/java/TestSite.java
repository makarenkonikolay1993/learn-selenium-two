import net.lightbody.bmp.core.har.Har;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
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
        for (WebElement el : countryNames) {
            notSortedNames.add(el.getText());
        }
        List<String> sortedNames = new ArrayList<String>(notSortedNames);

//        for (String s : notSortedNames) {
//            sortedNames.add(s);
//        }

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


    @Test
    public void registrationAndLogoutCheck() {
        int rnd = new Random().nextInt(10000);
        String firstName = "firstName" + rnd;
        String lastName = "lastName" + rnd;

        String address1 = "address1" + rnd;
        String postcode = "49000";
        String city = "city";
        String email = "email" + rnd + "@gmail.com";
        String phoneNumber = "+380955050500";
        String password = "qwerty";

        driver.navigate().to("http://localhost/litecart/");
        WebElement registrBtn = driver.findElement(By.cssSelector("form a"));
        registrBtn.click();

        List<WebElement> forms = driver.findElements(By.cssSelector("#create-account table tr"));
        forms.get(1).findElement(By.cssSelector("td:nth-of-type(1) input")).sendKeys(firstName);
        forms.get(1).findElement(By.cssSelector("td:nth-of-type(2) input")).sendKeys(lastName);
        forms.get(2).findElement(By.cssSelector("td:nth-of-type(1) input")).sendKeys(address1);
        forms.get(3).findElement(By.cssSelector("td:nth-of-type(1) input")).sendKeys(postcode);
        forms.get(3).findElement(By.cssSelector("td:nth-of-type(2) input")).sendKeys(city);
        forms.get(5).findElement(By.cssSelector("td:nth-of-type(1) input")).sendKeys(email);
        forms.get(5).findElement(By.cssSelector("td:nth-of-type(2) input")).sendKeys(phoneNumber);
        forms.get(7).findElement(By.cssSelector("td:nth-of-type(1) input")).sendKeys(password);
        forms.get(7).findElement(By.cssSelector("td:nth-of-type(2) input")).sendKeys(password);
        forms.get(8).findElement(By.cssSelector("td button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#box-account li a[href *= logout]"))));

        boolean logoutBtn = elementIsPresent(driver, By.cssSelector("#box-account li a[href *= logout]"));
        assertThat(logoutBtn).isTrue();
        driver.findElement(By.cssSelector("#box-account li a[href *= logout]")).click();
        List<WebElement> formsLogin = driver.findElements(By.cssSelector("#box-account-login table input"));
        formsLogin.get(0).sendKeys(email);
        formsLogin.get(1).sendKeys(password);
        driver.findElement(By.cssSelector("#box-account-login button[name=login]")).click();
        boolean logoutBtn2 = elementIsPresent(driver, By.cssSelector("#box-account li a[href *= logout]"));
        assertThat(logoutBtn2).isTrue();
    }

    @Test
    public void registrationAndLogoutCheckWithoutLists() {
        int rnd = new Random().nextInt(10000);
        String firstName = "firstName" + rnd;
        String lastName = "lastName" + rnd;

        String address1 = "address1" + rnd;
        String postcode = "49000";
        String city = "city";
        String email = "email" + rnd + "@gmail.com";
        String phoneNumber = "+380955050500";
        String password = "qwerty";

        driver.navigate().to("http://localhost/litecart/");
        WebElement registrBtn = driver.findElement(By.cssSelector("form a"));
        registrBtn.click();

        WebElement firstNameEl = driver.findElement(By.cssSelector("input[name=firstname]"));
        firstNameEl.sendKeys(firstName);

        WebElement lastNameEl = driver.findElement(By.cssSelector("input[name=lastname]"));
        lastNameEl.sendKeys(lastName);

        WebElement address1El = driver.findElement(By.cssSelector("input[name=address1]"));
        address1El.sendKeys(address1);

        WebElement postcodeEl = driver.findElement(By.cssSelector("input[name=postcode]"));
        postcodeEl.sendKeys(postcode + "1");

        WebElement cityEl = driver.findElement(By.cssSelector("input[name=city]"));
        cityEl.sendKeys(city);

        Select selectCountry = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        selectCountry.selectByValue("RU");

        WebElement emailEl = driver.findElement(By.cssSelector("input[name=email]"));

        emailEl.sendKeys(email);

        WebElement phoneEl = driver.findElement(By.cssSelector("input[name=phone]"));
        phoneEl.sendKeys(phoneNumber);

        WebElement passwordEl = driver.findElement(By.cssSelector("input[name=password]"));
        passwordEl.sendKeys(password);

        WebElement passwordConfirmEl = driver.findElement(By.cssSelector("input[name=confirmed_password]"));
        passwordConfirmEl.sendKeys(password);

        WebElement createAccBtn = driver.findElement(By.cssSelector("button[name=create_account]"));
        createAccBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#box-account li a[href *= logout]"))));

        boolean logoutBtn = elementIsPresent(driver, By.cssSelector("#box-account li a[href *= logout]"));
        assertThat(logoutBtn).isTrue();
        driver.findElement(By.cssSelector("#box-account li a[href *= logout]")).click();
        List<WebElement> formsLogin = driver.findElements(By.cssSelector("#box-account-login table input"));
        formsLogin.get(0).sendKeys(email);
        formsLogin.get(1).sendKeys(password);
        driver.findElement(By.cssSelector("#box-account-login button[name=login]")).click();
        boolean logoutBtn2 = elementIsPresent(driver, By.cssSelector("#box-account li a[href *= logout]"));
        assertThat(logoutBtn2).isTrue();
    }

    @Test
    public void newProductAddInAdminCheck() {
        int rnd = new Random().nextInt(10000);
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        WebElement catalog = driver.findElement(By.cssSelector("#box-apps-menu a[href *= catalog]"));
        catalog.click();

        WebElement newProductBtn = driver.findElement(By.cssSelector("#content a:nth-of-type(2)"));
        newProductBtn.click();

        List<WebElement> forms = driver.findElements(By.cssSelector("#tab-general tr"));

        WebElement status = forms.get(0).findElement(By.cssSelector("label:nth-of-type(1)"));
        status.click();

        WebElement productName = forms.get(1).findElement(By.cssSelector("input"));
        productName.sendKeys("nameProduct" + rnd);

        WebElement productCode = forms.get(2).findElement(By.cssSelector("input"));
        productCode.sendKeys("codeProduct" + rnd);

        WebElement productGroup = forms.get(6).findElement(By.xpath("(//input[@name='product_groups[]'])[3]"));
        productGroup.click();

        WebElement productQuantity = forms.get(7).findElement(By.xpath("//input[@name='quantity']"));
        productQuantity.clear();
        productQuantity.sendKeys("20,00");

        WebElement informationTab = driver.findElement(By.cssSelector("ul.index a[href *= information]"));
        informationTab.click();

        WebElement manufacturerSelect = driver.findElement(By.cssSelector("#tab-information select[name=manufacturer_id]"));

        Select select = new Select(manufacturerSelect);
        select.selectByValue("1");

        WebElement keywords = driver.findElement(By.cssSelector("input[name=keywords]"));
        keywords.sendKeys("keywords" + rnd);

        WebElement shortDescr = driver.findElement(By.cssSelector("input[name='short_description[en]']"));
        shortDescr.sendKeys("short Description" + rnd);

        WebElement headTitle = driver.findElement(By.cssSelector("input[name='head_title[en]']"));
        headTitle.sendKeys("head Title" + rnd);

        WebElement pricesTab = driver.findElement(By.cssSelector("a[href *= prices]"));
        pricesTab.click();

        WebElement purchasePrice = driver.findElement(By.cssSelector("input[name='purchase_price']"));
        purchasePrice.clear();
        purchasePrice.sendKeys("20");

        WebElement purchasePrice2 = driver.findElement(By.cssSelector("input[data-type='currency'"));
        purchasePrice2.clear();
        purchasePrice2.sendKeys("20");


        WebElement saveButton = driver.findElement(By.cssSelector(".button-set button:nth-of-type(1)"));
        saveButton.click();

        boolean checkProductAdd = elementIsPresent(driver, By.xpath("//a[contains(text(), 'nameProduct" + rnd + "')]"));
        assertThat(checkProductAdd).isTrue();
    }

    @Test
    public void newProductAddInAdminCheckWithoutLists() {
        int rnd = new Random().nextInt(10000);
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        WebElement catalog = driver.findElement(By.cssSelector("#box-apps-menu a[href *= catalog]"));
        catalog.click();

        WebElement newProductBtn = driver.findElement(By.cssSelector("#content a:nth-of-type(2)"));
        newProductBtn.click();

        WebElement status = driver.findElement(By.xpath("//label[.//input[@name='status']][1]"));
        status.click();

        WebElement productName = driver.findElement(By.cssSelector("input[name='name[en]']"));
        productName.sendKeys("nameProduct" + rnd);

        WebElement productCode = driver.findElement(By.cssSelector("input[name='code']"));
        productCode.sendKeys("codeProduct" + rnd);

        WebElement genderUnisex = driver.findElement(By.cssSelector("input[value='1-1']"));
        genderUnisex.click();

        WebElement quantity = driver.findElement(By.cssSelector("input[name=quantity]"));
        quantity.clear();
        quantity.sendKeys("20");



        //List<WebElement> forms = driver.findElements(By.cssSelector("#tab-general tr"));

//        WebElement status = forms.get(0).findElement(By.cssSelector("label:nth-of-type(1)"));
//        status.click();
//
//        WebElement productName = forms.get(1).findElement(By.cssSelector("input"));
//        productName.sendKeys("nameProduct" + rnd);
//
//        WebElement productCode = forms.get(2).findElement(By.cssSelector("input"));
//        productCode.sendKeys("codeProduct" + rnd);
//
//        WebElement productGroup = forms.get(6).findElement(By.xpath("(//input[@name='product_groups[]'])[3]"));
//        productGroup.click();
//
//        WebElement productQuantity = forms.get(7).findElement(By.xpath("//input[@name='quantity']"));
//        productQuantity.clear();
//        productQuantity.sendKeys("20,00");

        WebElement informationTab = driver.findElement(By.cssSelector("ul.index a[href *= information]"));
        informationTab.click();

        WebElement manufacturerSelect = driver.findElement(By.cssSelector("#tab-information select[name=manufacturer_id]"));

        Select select = new Select(manufacturerSelect);
        select.selectByValue("1");

        WebElement keywords = driver.findElement(By.cssSelector("input[name=keywords]"));
        keywords.sendKeys("keywords" + rnd);

        WebElement shortDescr = driver.findElement(By.cssSelector("input[name='short_description[en]']"));
        shortDescr.sendKeys("short Description" + rnd);

        WebElement headTitle = driver.findElement(By.cssSelector("input[name='head_title[en]']"));
        headTitle.sendKeys("head Title" + rnd);

        WebElement pricesTab = driver.findElement(By.cssSelector("a[href *= prices]"));
        pricesTab.click();

        WebElement purchasePrice = driver.findElement(By.cssSelector("input[name='purchase_price']"));
        purchasePrice.clear();
        purchasePrice.sendKeys("20");

        WebElement purchasePrice2 = driver.findElement(By.cssSelector("input[data-type='currency'"));
        purchasePrice2.clear();
        purchasePrice2.sendKeys("20");


        WebElement saveButton = driver.findElement(By.cssSelector(".button-set button:nth-of-type(1)"));
        saveButton.click();

        WebElement newProduct = driver.findElement(By.xpath("//a[contains(text(), 'nameProduct" + rnd + "')]"));
        newProduct.click();

        try {
            driver.findElement(By.cssSelector("p img"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("No image");
        }

        driver.navigate().back();

        boolean checkProductAdd = elementIsPresent(driver, By.xpath("//a[contains(text(), 'nameProduct" + rnd + "')]"));
        assertThat(checkProductAdd).isTrue();
    }

    @Test
    public void checkAddProductsInTrash() {
        driver.navigate().to("http://localhost/litecart/");
        int rnd = new Random().nextInt(10000);
        String firstName = "firstName" + rnd;
        String lastName = "lastName" + rnd;

        String address1 = "address1" + rnd;
        String postcode = "49000";
        String city = "city";
        String email = "email" + rnd + "@gmail.com";
        String phoneNumber = "+380955050500";
        String password = "qwerty";

        driver.navigate().to("http://localhost/litecart/");
        WebElement registrBtn = driver.findElement(By.cssSelector("form a"));
        registrBtn.click();

        WebElement firstNameEl = driver.findElement(By.cssSelector("input[name=firstname]"));
        firstNameEl.sendKeys(firstName);

        WebElement lastNameEl = driver.findElement(By.cssSelector("input[name=lastname]"));
        lastNameEl.sendKeys(lastName);

        WebElement address1El = driver.findElement(By.cssSelector("input[name=address1]"));
        address1El.sendKeys(address1);

        WebElement postcodeEl = driver.findElement(By.cssSelector("input[name=postcode]"));
        postcodeEl.sendKeys(postcode + "1");

        WebElement cityEl = driver.findElement(By.cssSelector("input[name=city]"));
        cityEl.sendKeys(city);

        Select selectCountry = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        selectCountry.selectByValue("RU");

        WebElement emailEl = driver.findElement(By.cssSelector("input[name=email]"));

        emailEl.sendKeys(email);

        WebElement phoneEl = driver.findElement(By.cssSelector("input[name=phone]"));
        phoneEl.sendKeys(phoneNumber);

        WebElement passwordEl = driver.findElement(By.cssSelector("input[name=password]"));
        passwordEl.sendKeys(password);

        WebElement passwordConfirmEl = driver.findElement(By.cssSelector("input[name=confirmed_password]"));
        passwordConfirmEl.sendKeys(password);

        WebElement createAccBtn = driver.findElement(By.cssSelector("button[name=create_account]"));
        createAccBtn.click();

        returnToHomePage(driver);

        addProduct(driver, "Yellow Duck", "Small");

        returnToHomePage(driver);

        addProduct(driver, "Yellow Duck", "Medium");

        returnToHomePage(driver);

        addProduct(driver, "Yellow Duck", "Large");

        WebElement korzina = driver.findElement(By.xpath("//a[text()='Checkout »']"));
        korzina.click();

        WebElement firstProductToRemove = driver.findElement(By.xpath("//li[@class='shortcut'][1]/a"));
        firstProductToRemove.click();

        WebElement codeProduct = driver.findElement(By.cssSelector("li.item:nth-of-type(1) p span"));
        String code = codeProduct.getText();

        WebElement removeBtn = driver.findElement(By.cssSelector("li.item:nth-of-type(1) p button[value=Remove]"));
        removeBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("#order_confirmation-wrapper tr td"), code));

    }

    @Test
    public void newProductAddInAdminCheckWithoutListsWithImage() {
        int rnd = new Random().nextInt(10000);
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        assertThat(driver.getTitle()).isEqualTo("My Store");

        WebElement catalog = driver.findElement(By.cssSelector("#box-apps-menu a[href *= catalog]"));
        catalog.click();

        WebElement newProductBtn = driver.findElement(By.cssSelector("#content a:nth-of-type(2)"));
        newProductBtn.click();

        WebElement status = driver.findElement(By.xpath("//label[.//input[@name='status']][1]"));
        status.click();

        WebElement productName = driver.findElement(By.cssSelector("input[name='name[en]']"));
        productName.sendKeys("nameProduct" + rnd);

        WebElement productCode = driver.findElement(By.cssSelector("input[name='code']"));
        productCode.sendKeys("codeProduct" + rnd);

        WebElement genderUnisex = driver.findElement(By.cssSelector("input[value='1-1']"));
        genderUnisex.click();

        WebElement quantity = driver.findElement(By.cssSelector("input[name=quantity]"));
        quantity.clear();
        quantity.sendKeys("20");

        WebElement uploadImg = driver.findElement(By.cssSelector("input[name='new_images[]']"));
        uploadImg.sendKeys("E:\\duck.png");

        WebElement informationTab = driver.findElement(By.cssSelector("ul.index a[href *= information]"));
        informationTab.click();

        WebElement manufacturerSelect = driver.findElement(By.cssSelector("#tab-information select[name=manufacturer_id]"));

        Select select = new Select(manufacturerSelect);
        select.selectByValue("1");

        WebElement keywords = driver.findElement(By.cssSelector("input[name=keywords]"));
        keywords.sendKeys("keywords" + rnd);

        WebElement shortDescr = driver.findElement(By.cssSelector("input[name='short_description[en]']"));
        shortDescr.sendKeys("short Description" + rnd);

        WebElement headTitle = driver.findElement(By.cssSelector("input[name='head_title[en]']"));
        headTitle.sendKeys("head Title" + rnd);

        WebElement pricesTab = driver.findElement(By.cssSelector("a[href *= prices]"));
        pricesTab.click();

        WebElement purchasePrice = driver.findElement(By.cssSelector("input[name='purchase_price']"));
        purchasePrice.clear();
        purchasePrice.sendKeys("20");

        WebElement purchasePrice2 = driver.findElement(By.cssSelector("input[data-type='currency'"));
        purchasePrice2.clear();
        purchasePrice2.sendKeys("20");


        WebElement saveButton = driver.findElement(By.cssSelector(".button-set button:nth-of-type(1)"));
        saveButton.click();

        WebElement newProduct = driver.findElement(By.xpath("//a[contains(text(), 'nameProduct" + rnd + "')]"));
        newProduct.click();

        try {
            driver.findElement(By.cssSelector("p img"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("No image");
        }

        driver.navigate().back();

        boolean checkProductAdd = elementIsPresent(driver, By.xpath("//a[contains(text(), 'nameProduct" + rnd + "')]"));
        assertThat(checkProductAdd).isTrue();
    }

    @Test
    public void newWindowsTest() throws Exception {
        driver.navigate().to("http://localhost/litecart/admin");

        WebElement loginForm = driver.findElement(By.cssSelector("#box-login"));
        WebElement loginInput = loginForm.findElement(By.cssSelector("[data-type=text]"));
        WebElement passwordInput = loginForm.findElement(By.cssSelector("[data-type=password]"));
        WebElement loginBtn = loginForm.findElement(By.cssSelector("[value=Login]"));

        loginInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        driver.findElement(By.xpath("//a[.//span[text()='Countries']]")).click();

        driver.findElement(By.cssSelector("a[href $=AF][title=Edit]")).click();

        String currentWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        driver.findElement(By.cssSelector("tbody tr:nth-of-type(2) a[target=_blank]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        String newWindow = wait.until(anyWindowOtherThan(oldWindows));

        driver.switchTo().window(newWindow);
        System.out.println(driver.getTitle());

        assertThat(driver.getTitle()).isEqualTo("ISO 3166-1 alpha-2 - Wikipedia");
        takeSnapShot(driver, "e://test.png");
        driver.close();
        driver.switchTo().window(currentWindow);
        System.out.println(driver.getTitle());
        assertThat(driver.getTitle()).isEqualTo("Edit Country | My Store");
    }

    @Test
    public void frameTest() {
        driver.navigate().to("https://jsbin.com/kitaqaf/edit?html,output");
        WebElement firstFrame = driver.findElement(By.cssSelector("#live iframe"));
        driver.switchTo().frame(firstFrame);
        WebElement secondFrame = driver.findElement(By.cssSelector("iframe"));
        driver.switchTo().frame(secondFrame);
        driver.findElement(By.cssSelector("input#test")).sendKeys("test 23 07 2020");
        driver.switchTo().parentFrame();
        //driver.switchTo().parentFrame();
        driver.findElement(By.cssSelector("#control"));
    }

    @Test
    public void getBrowserLogs() {
        driver.navigate().to("http://crm01-test/");
        System.out.println(driver.manage().logs().getAvailableLogTypes());
        driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
    }


    @Test
    public void getProxyLogs() {
        proxy.newHar();
        driver.navigate().to("http://crm01-test/");
        Har har = proxy.endHar();
        har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() + ":" + l.getRequest().getUrl()));
    }

    @Test
    public void testProzorro() {
        driver.navigate().to("http://vsh2019-dev9/prozorro/default.aspx");
    }


    public void acceptAlert (WebDriver driver) {
        Alert alert = (new WebDriverWait(driver, 10)).until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void returnToHomePage(WebDriver driver) {
        WebElement homePage = driver.findElement(By.xpath("//div[@id='header-wrapper']//a[.//img[contains(@src, 'logotype')]]"));
        homePage.click();
    }

    public ExpectedCondition<String> anyWindowOtherThan (final Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply (WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null ;
            }
        };
    }

    public void addProduct (WebDriver driver, String duck, String size) {
        WebElement duckProduct = driver.findElement(By.xpath("//div[@id='box-most-popular']//a[@title='" + duck + "'][1]"));
        duckProduct.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement itemsBefore = driver.findElement(By.cssSelector("#cart span.quantity"));
        String qntItemsBefore = itemsBefore.getText();

        try {
            WebElement select = driver.findElement(By.cssSelector("select[name='options[Size]']"));
            Select sizeSelect = new Select (select);
            sizeSelect.selectByValue(size);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("NO select in this product");
        }

        WebElement addButton = driver.findElement(By.cssSelector("button[name='add_cart_product']"));
        addButton.click();

//        try {
//            acceptAlert(driver);
//        } catch (UnhandledAlertException e) {
//            System.out.println("Alert is present");
//        }

        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("#cart span.quantity"), qntItemsBefore));

        //wait.until(ExpectedConditions.stalenessOf(itemsBefore));

        WebElement itemsAfter = driver.findElement(By.cssSelector("#cart span.quantity"));
        String qntItemsAfter = itemsAfter.getText();

        System.out.println(qntItemsBefore);
        System.out.println(qntItemsAfter);

        assertThat(qntItemsBefore).isNotEqualTo(qntItemsAfter);
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

