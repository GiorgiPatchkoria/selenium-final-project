import io.github.bonigarcia.wdm.WebDriverManager;
import data.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;

public class MoviePageTests {
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
    public void movieTest() {
        driver.get(Constants.swoopEventsURL);
        WebElement cookie = driver.findElement(By.xpath(Constants.cookieButtonXPATH));
        cookie.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // choosing first movies
        WebElement firstMovie = driver.findElement(By.xpath(Constants.firstMovieXPATH));
        Actions actions = new Actions(driver);
        actions.moveToElement(firstMovie).perform();
        WebElement buyButton = driver.findElement(By.xpath(Constants.buyButtonXPATH));
        js.executeScript("arguments[0].click();", buyButton);

        // selecting cavea east point
        WebElement caveaEastPoint = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Constants.caveaEastPointID)));
        js.executeScript("arguments[0].scrollIntoView();", caveaEastPoint);
        js.executeScript("arguments[0].click();", caveaEastPoint);
        js.executeScript("window.scrollBy(0,-100)");


        // validating that only cavea east point options are returned
        List<WebElement> cinemaNames = driver.findElements(By.xpath(Constants.cinemaNamesXPATH));
        boolean allCinemaIsCaveaEastPoint = cinemaNames.stream()
                .allMatch(name -> name.getText().contains(Constants.caveaEastPointTEXT));
        Assert.assertTrue(allCinemaIsCaveaEastPoint);

        String movieName = driver.findElement(By.xpath(Constants.movieNameXPATH)).getText();

        // choosing last day and getting date time of movie
        List<WebElement> days = driver.findElements(By.xpath(Constants.allDaysXPATH));
        WebElement lastDay = days.get(days.size() - 1);
        String dateTime = lastDay.getText().substring(0, 2);
        lastDay.click();


        // choosing last option and getting cinema name
        List<WebElement> options = driver.findElements(By.xpath(Constants.allOptionsXPATH));
        WebElement lastOption = options.get(options.size() - 1);
        String cinemaName = lastOption.getText();
        lastOption.click();


        // validate that movie name, cinema and datetime is correct in popup window
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Constants.popupXPATH)));
        String movieNameInPopup = driver.findElement(By.xpath(Constants.movieNameInPopupXPATH)).getText();
        String cinemaNameInPopup = driver.findElement(By.xpath(Constants.cinemaNameInPopupXPATH)).getText();
        String dateTimeInPopup = driver.findElement(By.xpath(Constants.dateTimeInPopupXPATH)).getText();
        boolean dateMatches = dateTimeInPopup.contains(dateTime);

        Assert.assertEquals(movieNameInPopup, movieName);
        Assert.assertEquals(cinemaNameInPopup, cinemaName);
        Assert.assertTrue(dateMatches);

        // choosing free seat
        List<WebElement> allFreeSeats = driver.findElements(By.xpath(Constants.freeSeatsXPATH));
        WebElement firstSeat = allFreeSeats.get(0);
        js.executeScript("arguments[0].click();", firstSeat);

        // creating new account and filling the form
        WebElement registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Constants.registerButtonXPATH)));
        registerButton.click();

        WebElement mailInput = driver.findElement(By.id(Constants.emailID));
        WebElement passwordInput = driver.findElement(By.id(Constants.passwordID));
        WebElement passwordReTypeInput = driver.findElement(By.id(Constants.passwordReTypeID));
        WebElement genderRadioButton = driver.findElement(By.id(Constants.maleRadioID));
        WebElement nameInput = driver.findElement(By.id(Constants.nameID));
        WebElement surnameInput = driver.findElement(By.id(Constants.surnameID));
        WebElement yearDropDown = driver.findElement(By.xpath(Constants.birthYearXPATH));
        WebElement phoneInput = driver.findElement(By.id(Constants.phoneID));
        WebElement phoneCodeInput = driver.findElement(By.id(Constants.phoneCodeID));
        WebElement firstCheckBox = driver.findElement(By.id(Constants.firstCheckBoxID));
        WebElement secondCheckBox = driver.findElement(By.id(Constants.secondCheckBoxID));
        WebElement confirmationButton = driver.findElement(By.id(Constants.confirmationButtonID));

        mailInput.sendKeys("gio.pachkoria2003");
        passwordInput.sendKeys("Paroli123");
        passwordReTypeInput.sendKeys("Paroli123");
        js.executeScript("arguments[0].click();", genderRadioButton);
        nameInput.sendKeys("giorgi");
        surnameInput.sendKeys("patchkoria");
        js.executeScript("arguments[0].value = arguments[1]", yearDropDown, "2003");
        phoneInput.sendKeys("597046467");
        phoneCodeInput.sendKeys("1111");
        js.executeScript("arguments[0].click();", firstCheckBox);
        js.executeScript("arguments[0].click();", secondCheckBox);
        js.executeScript("arguments[0].click();", confirmationButton);

        // validate that error message has appeared
        String errorMessage = driver.findElement(By.id(Constants.errorMessageID)).getText();
        String expectedMessage = Constants.messageTEXT;
        try{
            Assert.assertEquals(errorMessage, expectedMessage);
        } catch (AssertionError e){
            System.out.println("Error message is incorrect");
        }

    }
}
