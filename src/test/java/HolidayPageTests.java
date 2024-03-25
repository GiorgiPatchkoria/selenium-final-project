import io.github.bonigarcia.wdm.WebDriverManager;
import data.Constants;
import util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.Collections;
import java.util.List;

public class HolidayPageTests {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeTest
    @Parameters("browser")
    public void setup(String browser){
        if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }

    @Test
    public void descendingOrderTest() {
        driver.get(Constants.swoopDasvenebaURL);

        // going on every page to get ALL offers prices
        // then sorting it in descending order and getting most expensive one
        Util offers = new Util(driver);
        List<Integer> allPrices = offers.getAllPrices();
        allPrices.sort(Collections.reverseOrder());
        int biggestPrice = allPrices.get(0);

        // clicking on ფასით კლებადი button to sort offers
        Select sortOffers = new Select(driver.findElement(By.xpath(Constants.sortingXPATH)));
        sortOffers.selectByVisibleText(Constants.descendingTEXT);

        wait.until(ExpectedConditions.urlContains(Constants.partOfSortURL));

        // getting first offer and validate that it is most expensive price out of all offers
        int priceNumber = offers.gettingFirstOfferPrice();
        try {
            Assert.assertEquals(biggestPrice, priceNumber);
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }

    }

    @Test
    public void ascendingOrderTest(){
        driver.get(Constants.swoopDasvenebaURL);
        WebElement cookie = driver.findElement(By.xpath(Constants.cookieButtonXPATH));
        cookie.click();

        // going on every page to get ALL offers prices
        // then sorting it in ascending order and getting least expensive one
        Util offers = new Util(driver);
        List<Integer> allPrices = offers.getAllPrices();
        Collections.sort(allPrices);
        int smallestPrice = allPrices.get(0);

        // clicking on ფასით ზრდადი button to sort offers
        Select sortOffers = new Select(driver.findElement(By.xpath(Constants.sortingXPATH)));
        sortOffers.selectByVisibleText(Constants.ascendingTEXT);


        wait.until(ExpectedConditions.urlContains(Constants.partOfSortURL));


        // getting first offer and validate that least expensive one out of all offers is first offer
        int priceNumber = offers.gettingFirstOfferPrice();
        try{
            Assert.assertEquals(smallestPrice, priceNumber);
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }

    @Test
    public void filterTest() {
        driver.get(Constants.swoopDasvenebaURL);

        // clicking on კოტეჯი checkbox
        WebElement kotejiCheckBox = driver.findElement(By.xpath(Constants.kotejiCheckBoxXPATH));
        kotejiCheckBox.click();

        wait.until(ExpectedConditions.urlContains(Constants.partOfKotejiURL));

        // going on every page to get ALL offers descriptions and checking if all offers contain 'კოტეჯი'
        Util offers = new Util(driver);
        List<String> descriptionsList = offers.getAllDescriptions();
        boolean allOffersContainKoteji = descriptionsList.stream()
                .allMatch(description -> description.contains("კოტეჯი") || description.contains("კოტეჯები"));

        // validate that all offers contain word 'კოტეჯი'
        try {
            Assert.assertTrue(allOffersContainKoteji);
        } catch (AssertionError e){
            System.out.println("Not all offers contain word 'კოტეჯი'");
        }

        // going to first page in order to start it from beginning and to get all offers prices
        // then sorting prices and getting smallest one
        WebElement firstPageButton = driver.findElement(By.xpath(Constants.backToFirstPageXPATH));
        firstPageButton.click();
        List<Integer> allPrices = offers.getAllPrices();
        Collections.sort(allPrices);
        int smallestPrice = allPrices.get(0);

        // clicking on ფასით ზრდადი button to sort offers
        Select sortOffers = new Select(driver.findElement(By.xpath(Constants.sortingXPATH)));
        sortOffers.selectByVisibleText(Constants.ascendingTEXT);

        wait.until(ExpectedConditions.urlContains(Constants.partOfSortURL));

        // getting first offer and validate that least expensive one out of all offers is first offer
        int priceNumber = offers.gettingFirstOfferPrice();
        try{
            Assert.assertEquals(smallestPrice, priceNumber);
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }

    @Test
    public void priceRangeTest() {
        driver.get(Constants.swoopDasvenebaURL);

        // entering values in price range and clicking search button
        WebElement inputMinPrice = driver.findElement(By.xpath(Constants.inputMinPriceXPATH));
        WebElement inputMaxPrice = driver.findElement(By.xpath(Constants.inputMaxPriceXPATH));
        inputMinPrice.sendKeys("300");
        inputMaxPrice.sendKeys("1500");
        WebElement searchButton = driver.findElement(By.xpath(Constants.searchButtonXPATH));
        searchButton.click();

        wait.until(ExpectedConditions.urlContains(Constants.partOfPriceRangeURL));

        // going on every page to get ALL offers prices
        // then we check if all offers are in our given price range
        Util offers = new Util(driver);
        List<Integer> allPrices = offers.getAllPrices();
        boolean allOffersContainKoteji = allPrices.stream()
                .allMatch(prices -> prices >= 300 && prices <= 1500);

        // validate that all offers are in our given price range
        try {
            Assert.assertTrue(allOffersContainKoteji);
        } catch (AssertionError e){
            System.out.println("Some offers should not be in these offers because their price does not meet price range");
        }
    }
}
