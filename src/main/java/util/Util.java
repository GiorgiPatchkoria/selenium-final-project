package util;

import data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private WebDriver driver;

    public Util(WebDriver driver) {
        this.driver = driver;
    }

    public List<Integer> getAllPrices() {
        List<Integer> pricesList = new ArrayList<>();

        int currentPage = 1;
        boolean hasNextPage = true;
        while (hasNextPage) {
            // getting all offers prices, making them integer and adding them into pricesList
            List<WebElement> priceOfOffers = driver.findElements(By.xpath(Constants.pricesOfOffersXPATH));
            for (WebElement price : priceOfOffers){
                String priceString = price.getText();
                int priceNumber = Integer.parseInt(priceString.substring(0, priceString.length() - 1));
                pricesList.add(priceNumber);
            }

            // check if we have offers in other pages
            // if we have, we go there and get offers again
            WebElement nextPageButton = null;
            try {
                nextPageButton = driver.findElement(By.linkText(String.valueOf(currentPage + 1)));
            } catch (NoSuchElementException e) {
                hasNextPage = false;
            }
            if (hasNextPage) {
                nextPageButton.click();
                currentPage++;
            }
        }
        return pricesList;
    }

    public List<String> getAllDescriptions() {
        List<String> descriptionsList = new ArrayList<>();

        int currentPage = 1;
        boolean hasNextPage = true;
        while (hasNextPage) {
            // getting all offers descriptions and adding them into descriptionsList
            List<WebElement> descriptionOfOffers = driver.findElements(By.xpath(Constants.descriptionOfOffersXPATH));
            for (WebElement description : descriptionOfOffers){
                descriptionsList.add(description.getText());
            }


            // check if we have offers in other pages
            // if we have, we go there and get offers again
            WebElement nextPageButton = null;
            try {
                nextPageButton = driver.findElement(By.linkText(String.valueOf(currentPage + 1)));
            } catch (NoSuchElementException e) {
                hasNextPage = false;
            }
            if (hasNextPage) {
                nextPageButton.click();
                currentPage++;
            }
        }
        return descriptionsList;
    }

    public int gettingFirstOfferPrice() {
        // getting price of first offer and making it integer
        WebElement firstOfferPrice = driver.findElement(By.xpath(Constants.priceOfFirstOfferXPATH));
        String priceText = firstOfferPrice.getText();
        return Integer.parseInt(priceText.substring(0, priceText.length() - 1));
    }
}
