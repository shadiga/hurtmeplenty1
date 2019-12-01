

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PricingCalculatorPage {
    public WebDriver driver;

    public PricingCalculatorPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[contains(@ng-model,'quantity')]")
    WebElement numberOfInstance;

    @FindBy(xpath = "//md-select[@placeholder='VM Class']")
    WebElement machineClassCheckBox;

    @FindBy(xpath = "//md-select-value/span/div[contains(text(),'Free')]/../../..")
    WebElement operationSystemCheckBox;

    @FindBy(xpath = "//md-select[@placeholder='Instance type']")
    WebElement machineType;

    @FindBy(xpath = "//md-input-container/md-checkbox/div[contains(text(),'Add GPUs')]")
    WebElement gpuCheckBox;

    @FindBy(xpath = "//md-select[@placeholder='Number of GPUs']")
    WebElement gpuQuantityCheckBox;

    @FindBy(xpath = "//md-select[@placeholder='GPU type']")
    WebElement gpuTypeCheckBox;

    @FindBy(xpath = "//md-select[@placeholder='Local SSD']")
    WebElement ssdCheckBox;

    @FindBy(xpath = "//md-select[@placeholder='Datacenter location' and contains(@ng-model,'computeServer')]")
    WebElement dataCenterLocationCheckBox;

    @FindBy(xpath = "//md-select[@placeholder='Committed usage'  and contains(@ng-change,'onCudChange')]")
    WebElement committedUsageTimeCheckBox;

    @FindBy(xpath = "//button[@aria-label='Add to Estimate'] [not(contains(@disabled,'disabled'))]")
    WebElement buttonAddToEstimate;


    public PricingCalculatorPage inputNumberOfInstance(Integer quantity){
        driver.switchTo().frame("myFrame");
        numberOfInstance.sendKeys(quantity.toString());
        return this;
    }

    public PricingCalculatorPage chooseOperationSystem(String operationSystemName){
        openCheckBox(operationSystemCheckBox);
        webElementWaitToBeClickableAndClick("//md-option/div[contains(text(),'" +
                operationSystemName + "')]/..");
        return this;
    }

    public PricingCalculatorPage chooseMachineClass(String machineClassName){
        openCheckBox(machineClassCheckBox);
        webElementWaitToBeClickableAndClick("//div[contains(@class,'md-active')]//md-option[contains(.,'"
                + machineClassName + "')]");
        return this;
    }

    public PricingCalculatorPage inputMachineType(String typeOfMachine) {
        openCheckBox(machineType);
        webElementWaitToBeClickableAndClick("//md-option[contains(@value,'8') and contains(@value,'STANDARD')]");
        return this;
    }

    public PricingCalculatorPage addGPU(int numberOfGPU, String gpuType){
        gpuCheckBox.click();
        openCheckBox(gpuQuantityCheckBox);
        webElementWaitToBeClickableAndClick("//md-option[contains(@ng-disabled, " +
                "'GPU')]/div[contains(text(),'" + numberOfGPU + "')]");
        openCheckBox(gpuTypeCheckBox);
        webElementWaitToBeClickableAndClick("//md-option/div[contains(text(),'" + gpuType + "')]");
        return this;
    }

    public PricingCalculatorPage selectSSD(int ssdQuantity){
        openCheckBox(ssdCheckBox);
        webElementWaitToBeClickableAndClick("//md-option[contains(@ng-repeat,'supportedSsd') and @value='" +
                ssdQuantity + "']");
        return this;
    }

    public PricingCalculatorPage selectDataCenterLocation(String location){
        openCheckBox(dataCenterLocationCheckBox);
        webElementWaitToBeClickableAndClick("//div[@aria-hidden = 'false']//div[contains(text(),'"
                + location + "')]/..");
        return this;
    }

    public PricingCalculatorPage selectCommittedUsage(int timeOfUsage){
        openCheckBox(committedUsageTimeCheckBox);
        webElementWaitToBeClickableAndClick("//div[contains(@class,'md-active')]//md-option[@value='"
                + timeOfUsage + "']");
        return this;
    }

    public PricingCalculatorPage addToEstimate(){
        webElementWaitToBeClickableAndClick(buttonAddToEstimate);
        return this;
    }

    protected void webElementWaitToBeClickableAndClick(WebElement clickableWebElement){
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(clickableWebElement)).click();
    }

    protected void webElementWaitToBeClickableAndClick(String pathToWebElement){
        new WebDriverWait(driver, 20).until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(pathToWebElement)));
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(pathToWebElement))).click();
    }

    protected void openCheckBox(WebElement openedCheckBox){
        while (openedCheckBox.getAttribute("aria-expanded" ).equalsIgnoreCase("false")){
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(openedCheckBox))
                    .click();
        }
    }
}
