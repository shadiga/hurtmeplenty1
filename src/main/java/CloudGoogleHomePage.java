

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CloudGoogleHomePage {
    public static final String HOME_PAGE = "https://cloud.google.com";
    WebDriver driver;
    String searchTextLink;

    @FindBy(xpath = "//input[contains(@class, 'devsite-search-field')]")
    WebElement searchButton;

    @FindBy(xpath = "//input[@name = 'q']")
    WebElement searchForm;

    @FindBy(className = "devsite-search-results")
    WebElement searchResults;

    public CloudGoogleHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CloudGoogleHomePage openHomePage(){
        driver.get(HOME_PAGE);
        new WebDriverWait(driver, 10);
        return this;
    }

    public CloudGoogleHomePage enterASearchQueryAndGetResult(String searchText){
        searchTextLink = searchText;
        searchButton.click();
        searchForm.sendKeys(searchText);
        searchForm.sendKeys(Keys.ENTER);
        return this;
    }

    public PricingCalculatorPage clickOnFirstLinkInSearchResult(){
        searchResults.findElement(By.linkText(searchTextLink)).click();
        return new PricingCalculatorPage(driver);
    }
}
