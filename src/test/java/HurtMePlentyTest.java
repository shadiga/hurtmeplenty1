
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class HurtMePlentyTest {
    WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void openBrowser(){
        driver = new ChromeDriver();
        //driver = new EdgeDriver();
        //driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test()
    public void openPage(){
        PricingCalculatorPage cloudGoogleHomePage = new CloudGoogleHomePage(driver)
                .openHomePage()
                .enterASearchQueryAndGetResult("Google Cloud Platform Pricing Calculator")
                .clickOnFirstLinkInSearchResult()
                .inputNumberOfInstance(4)
                .chooseOperationSystem("Free")
                .chooseMachineClass("Regular")
                .inputMachineType("n1-standard-8")
                .addGPU(1, "V100")
                .selectSSD(2)
                .selectDataCenterLocation("Frank")
                .selectCommittedUsage(1)
                .addToEstimate()
                ;
    }

    @Test(dependsOnMethods = "openPage")
    public void checkVMClass(){
        WebElement vmClassAfterEstimate = driver
                .findElement(By.xpath("//md-list-item/div[contains(text(),'VM class')]"));
        Assert.assertTrue(vmClassAfterEstimate.getText().contains("regular"));
    }

    @Test(dependsOnMethods = "openPage")
    public void checkInstanceType(){
        WebElement instanceTypeAfterEstimate = driver
                .findElement(By.xpath("//md-list-item/div[contains(text(),'Instance type')]"));
        Assert.assertTrue(instanceTypeAfterEstimate.getText().contains("n1-standard-8"));
    }

    @Test(dependsOnMethods = "openPage")
    public void checkRegion(){
        WebElement instanceTypeAfterEstimate = driver
                .findElement(By.xpath("//md-list-item/div[contains(text(),'Region')]"));
        Assert.assertTrue(instanceTypeAfterEstimate.getText().contains("Frankfurt"));
    }

    @Test(dependsOnMethods = "openPage")
    public void checkLocalSSD(){
        WebElement instanceTypeAfterEstimate = driver
                .findElement(By.xpath("//md-list-item/div[contains(text(),'local SSD')]"));
        Assert.assertTrue(instanceTypeAfterEstimate.getText().contains("2x375"));
    }

    @Test(dependsOnMethods = "openPage")
    public void checkCommitmentTerm(){
        WebElement instanceTypeAfterEstimate = driver
                .findElement(By.xpath("//md-list-item/div[contains(text(),'Commitment term')]"));
        Assert.assertTrue(instanceTypeAfterEstimate.getText().contains("1 Year"));
    }

    @Test(dependsOnMethods = "openPage")
    public void checkEstimatedComponentCost(){
        WebElement instanceTypeAfterEstimate = driver
                .findElement(By.xpath("//md-content[@id='compute']//b"));
        Assert.assertTrue(instanceTypeAfterEstimate.getText().contains("1,187.77"));
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        driver = null;
    }


}
