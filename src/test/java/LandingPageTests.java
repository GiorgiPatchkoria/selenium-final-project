import io.github.bonigarcia.wdm.WebDriverManager;
import data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LandingPageTests {
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
        wait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }

    @Test
    public void activeCategoryTest() {
        driver.get(Constants.swoopMainPageURL);

        // accepting cookie cause it prevents us from going to kartingi page
        WebElement cookie = driver.findElement(By.xpath(Constants.cookieButtonXPATH));
        cookie.click();

        // navigating to kartingi page: category -> sport -> kartingi
        WebElement categoryButton = driver.findElement(By.xpath(Constants.categoryButtonXPATH));
        categoryButton.click();
        WebElement sportButton = driver.findElement(By.xpath(Constants.sportButtonXPATH));
        Actions actions = new Actions(driver);
        actions.moveToElement(sportButton).perform();
        WebElement kartingiButton = driver.findElement(By.xpath(Constants.kartingiButtonXPATH));
        kartingiButton.click();

        // wait until page completely loads and then checking if urls match
        wait.until(ExpectedConditions.urlContains(Constants.partOfKartingiURL));
        String expectedURL = Constants.swoopKartingiURL;
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, expectedURL);

        // getting color and then converting it from rgb to hex, then validate if they match
        WebElement kartingi = driver.findElement(By.xpath(Constants.categoriesKartingXPATH));
        String colorInRGB = kartingi.getCssValue("color");
        Pattern pattern = Pattern.compile("\\((\\d+),\\s*(\\d+),\\s*(\\d+)");
        Matcher matcher = pattern.matcher(colorInRGB);
        String colorInHEX = "";
        if (matcher.find()) {
            int r = Integer.parseInt(matcher.group(1));
            int g = Integer.parseInt(matcher.group(2));
            int b = Integer.parseInt(matcher.group(3));
            colorInHEX = String.format("#%02X%02X%02X", r, g, b);
        }
        Assert.assertEquals("#6E7CFA", colorInHEX);
    }

    @Test
    public void logoTest(){
        driver.get(Constants.swoopMainPageURL);
        driver.navigate().to(Constants.swoopDasvenebaURL);

        WebElement logo = driver.findElement(By.xpath(Constants.swoopLogoXPATH));
        logo.click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, Constants.swoopMainPageURL);
    }
}
