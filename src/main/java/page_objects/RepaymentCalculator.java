package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RepaymentCalculator {
    /**
     * This Class is a page object class for repayment calculation
     */

    final public String pageURI = "digital/home-buying/calculator/home-loan-repayments";
    public WebDriver driver;
    public WebDriverWait wait;

    public RepaymentCalculator(WebDriver driver) {
        /***
         * Constructor for the class
         * @param: driver: webdriver
         */
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    public void navigateToRepaymentLink(String baseUrl) {
        /***
         * This method is used to navigate to repayment url
         * @param baseUrl: baseurl of the test
         */
        driver.get(baseUrl + pageURI);
    }

    public String getPageTitle() {
        /***
         * This method returs the page title
         * @return String: current web page title
         */
        return driver.getTitle();
    }

    //HomeLoan Repayment Calculator Form Submit
    public void submitForm(HashMap<String, String> testData) {
        /***
         * This method iterates over given testdata and fills in form
         * @param testData: key, value of form elements
         */
        Iterator it = testData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getKey().equals("RepaymentType")) {
                selectRepaymentType(pair.getValue().toString());
            }
            if (pair.getKey().equals("BorrowingAmount")) {
                enterBorrowingAmount(pair.getValue().toString());
            }
            if (pair.getKey().equals("LoanTerm")) {
                enterLoanTerm(pair.getValue().toString());
            }
            if (pair.getKey().equals("TotalRepaymentsAmount")) {
                selectRepaymentType(pair.getValue().toString());
            }
            if (pair.getKey().equals("ProductId")) {
                selectProductId(pair.getValue().toString());
            }
            if (pair.getKey().equals("customInterestRate")) {
                setInterestRate(pair.getValue().toString());
            }
            it.remove();
        }
        clickSubmit();
    }

    private void clickSubmit() {
        /***
         * clicks on submit button
         */
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
            driver.findElement(By.id("submit")).click();
        } catch (Exception e) {
        }
    }

    private void enterBorrowingAmount(String loanAmount) {
        /***
         * This method enters borrowing amount field
         * @param loanAmount: Amount borrowed by the user
         */

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amount")));
        try {
            driver.findElement(By.id("amount")).clear();
            driver.findElement(By.id("amount")).sendKeys(loanAmount);
        } catch (TimeoutException e) {
        }
    }

    private void enterLoanTerm(String loanTerm) {
        /**
         * This function enters loan term value in home loan calculator
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("term")));
        try {
            driver.findElement(By.id("term")).clear();
            driver.findElement(By.id("term")).sendKeys(loanTerm);
        } catch (TimeoutException e) {
        }
    }

    private void selectRepaymentType(String repaymentType) {
        /**
         * This function enters repayment type value in home loan calculator
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("interestOnly")));
        Select dropdown = new Select(driver.findElement(By.id("interestOnly")));
        List<WebElement> allOptions = driver.findElements(By.xpath("//*[@id = 'interestOnly']/option[@value[string()]]"));
        for (WebElement currentOption : allOptions) {
            if (currentOption.getText().toLowerCase().equals(repaymentType.toLowerCase())) {
                dropdown.selectByValue(currentOption.getAttribute("value"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id = 'interestOnly']/option[@value='" + currentOption.getAttribute("value") + "']")));
                return;
            }
        }
    }

    private void setInterestRate(String interestRate) {
        /**
         * This function sets interest rate in drop down list
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("useProductList")));
        driver.findElement(By.id("useProductList")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customRate")));
        driver.findElement(By.id("customRate")).clear();
        driver.findElement(By.id("customRate")).sendKeys(interestRate);
    }

    private void selectProductId(String productID) {
        /**
         * This function sets productId
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productId")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productId")));
        Select dropdown = new Select(driver.findElement(By.id("productId")));
        List<WebElement> allOptions = driver.findElements(By.xpath("//*[@id = 'productId']/option[@value[string()]]"));
        for (WebElement currentOption : allOptions) {
            if (currentOption.getText().toLowerCase().equals(productID.toLowerCase())) {
                dropdown.selectByValue(currentOption.getAttribute("value"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id = 'productId']/option[@value='" + currentOption.getAttribute("value") + "']")));
                return;
            }
        }
    }

    //This function verifies interest charged, repayments
    public HashMap<String, String> getRepaymentCalculations() {
        /**
         * This function verifies Total interest charged, Total Repayments and Monthly Repayment
         */
        HashMap<String, String> repaymentSummary = new HashMap<String, String>();
        repaymentSummary.put("MonthlyRepayment", getMonthlyRepayment());
        repaymentSummary.put("TotalInterestCharged", getTotalInterestCharged());
        repaymentSummary.put("TotalRepayments", getTotalRepayments());
        String monthlyRepaymentAfterInterestPeriodEnds = getMonthlyRepaymentAfterInterestPeriodEnds();
        if (!monthlyRepaymentAfterInterestPeriodEnds.equals("None")) {
            repaymentSummary.put("MonthlyRepaymentsAfterInterestPeriodEnds", monthlyRepaymentAfterInterestPeriodEnds);
        }
        return repaymentSummary;
    }

    private String getMonthlyRepayment() {
        /**
         * This function gets Monthly Repayment value
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-tid = \"repayment-amount\"]")));
        String repayments = driver.findElement(By.xpath("//*[@data-tid = \"repayment-amount\"]")).getText().replaceAll("\\$", "");
        return repayments.replaceAll(",", "");
    }

    private String getTotalRepayments() {
        /**
         * This function gets Total Repayments Value
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-tid = \"total-repayment\"]")));
        String repayments = driver.findElement(By.xpath("//*[@data-tid = \"total-repayment\"]")).getText().replaceAll("\\$", "");
        return repayments.replaceAll(",", "");
    }

    private String getTotalInterestCharged() {
        /**
         * This function gets Total Interest Charged value
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-tid = \"total-interest\"]")));
        String interest = driver.findElement(By.xpath("//*[@data-tid = \"total-interest\"]")).getText().replaceAll("\\$", "");
        return interest.replaceAll(",", "");
    }

    private String getMonthlyRepaymentAfterInterestPeriodEnds() {
        /**
         * This function gets monthly repayment after interest period ends
         */
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-tid = \"secondary-repayment-amount\"]")));
            String repayments = driver.findElement(By.xpath("//*[@data-tid = \"secondary-repayment-amount\"]")).getText().replaceAll("\\$", "");
            return repayments.replaceAll(",", "");
        } catch (Exception e) {
            return "None";
        }
    }

}
