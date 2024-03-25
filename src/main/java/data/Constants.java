package data;

public class Constants {
    // links
    public static final String swoopMainPageURL = "https://www.swoop.ge/";
    public static final String swoopDasvenebaURL = "https://www.swoop.ge/category/24/dasveneba";
    public static final String swoopKartingiURL = "https://www.swoop.ge/category/2058/sporti/kartingi";
    public static final String swoopEventsURL = "https://www.swoop.ge/events";


    // xpath
    public static final String pricesOfOffersXPATH = "//div[@class='discounted-prices']//p[@class='deal-voucher-price'][1]";
    public static final String priceOfFirstOfferXPATH = "//div[@class='special-offer'][1]//div[@class='discounted-prices']//p[@class='deal-voucher-price'][1]";
    public static final String sortingXPATH = "//select[@id='sort']";
    public static final String kotejiCheckBoxXPATH = "//div[@id='sidebar-container']//input[@type='checkbox' and @value='2']";
    public static final String descriptionOfOffersXPATH = "//div[contains(@class, 'special-offer-text')]//a";
    public static final String backToFirstPageXPATH = "//div[@class='pager-filter'][1]";
    public static final String inputMinPriceXPATH = "//div[@id='sidebar-container']//input[@id='minprice']";
    public static final String inputMaxPriceXPATH = "//div[@id='sidebar-container']//input[@id='maxprice']";
    public static final String searchButtonXPATH = "//div[@id='sidebar-container']//div[@class='submit-button']";
    public static final String cookieButtonXPATH = "//div[@class='acceptCookie']";
    public static final String categoryButtonXPATH = "//p[@class='categoriesTitle']";
    public static final String sportButtonXPATH = "//li[@class='leftSideMenu catId-6']";
    public static final String kartingiButtonXPATH = "//li[a='კარტინგი']//a";
    public static final String categoriesKartingXPATH = "//div[@id='sidebar-container']//span[@class='main-category-span open searched-category']";
    public static final String swoopLogoXPATH = "//a[@class='Newlogo']";
    public static final String firstMovieXPATH = "//div[@class='movies-deal'][1]";
    public static final String buyButtonXPATH = "//div[@class='movies-deal'][1]//div[@class='info-cinema-ticket']//parent::a";
    public static final String cinemaNamesXPATH = "//div[@id='384933']//div[@aria-hidden='false']//p[@class='cinema-title']";
    public static final String movieNameXPATH = "//div[@class='info']//p[@class='name']";
    public static final String allDaysXPATH = "//div[@id='384933']//ul//li";
    public static final String allOptionsXPATH = "//div[@id='384933']//div[@aria-hidden='false']//p[@class='cinema-title']";
    public static final String popupXPATH = "//div[@class='tickets-corns']";
    public static final String movieNameInPopupXPATH = "//div[@class='content-header']//p[@class='movie-title']";
    public static final String cinemaNameInPopupXPATH = "//div[@class='content-header']//p[@class='movie-cinema'][1]";
    public static final String dateTimeInPopupXPATH = "//div[@class='content-header']//p[@class='movie-cinema'][2]";
    public static final String freeSeatsXPATH = "//div[@class='seat free']";
    public static final String registerButtonXPATH = "//div[@class='create pl-2 ']//a";
    public static final String emailID = "email";
    public static final String passwordID = "password";
    public static final String passwordReTypeID = "PasswordRetype";
    public static final String maleRadioID = "Gender1";
    public static final String nameID = "name";
    public static final String surnameID = "surname";
    public static final String birthYearXPATH = "//select[@name='birth_year']";
    public static final String phoneID = "Phone";
    public static final String phoneCodeID = "PhoneCode";
    public static final String firstCheckBoxID = "test";
    public static final String secondCheckBoxID = "tbcAgreement";
    public static final String confirmationButtonID = "registrationBtn";
    public static final String caveaEastPointID = "ui-id-5";
    public static final String errorMessageID = "input-error-email";

    // text
    public static final String descendingTEXT = "ფასით კლებადი";
    public static final String ascendingTEXT = "ფასით ზრდადი";
    public static final String caveaEastPointTEXT = "კავეა ისთ ფოინთი";
    public static final String messageTEXT = "მეილის ფორმატი არასწორია!";
    public static final String partOfSortURL = "sortID";
    public static final String partOfKotejiURL = "arrangements";
    public static final String partOfPriceRangeURL = "price";
    public static final String partOfKartingiURL = "kartingi";

}
