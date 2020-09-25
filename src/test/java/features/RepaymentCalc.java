package features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_objects.RepaymentCalculator;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;
import static utils.CSVParser.parseCSV;

public class RepaymentCalc {
        public final String baseUrl="https://www.commbank.com.au/";
        public WebDriver driver;
        /**
         * The ParseTransaction Step definition implements cucumber scripted scenarios.
         * This Step definition class verifies if file exists, parse and generates CSV.
         * @author  Kali Vallepu
         * @version 1.0.0
         * @since   2020-09-25
         */

        HashMap<String, String> repaymentsTestData;
        RepaymentCalculator repaymentCalculator;

        //initialising chrome dri ver
    @Before
    public void initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/webdrivers/chromedriver");
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        repaymentCalculator = new RepaymentCalculator(driver);
    }

    //loads test data from CSV file
    @Given("^Load test data from csv (.*)$")
    public void load_test_data_from_csv(String fileName) throws Throwable{
            File file = new File("src/testData/"+fileName);
            assertTrue(file.exists() && !file.isDirectory());
        repaymentsTestData = parseCSV("src/testData/"+fileName);
        }

        // Naivgating to Repayment Calculator page
    @When("^User navigate to Repayment Calculator page$")
    public void user_navigate_to_Repayment_Calculator_page() {
           driver.get(baseUrl);
           repaymentCalculator.navigateToRepaymentLink(baseUrl);
         assertEquals(repaymentCalculator.getPageTitle(), "Repayments Calculator");
        }

        //Enter test data in home loan repayment calculator
    @When("^User submit repayment inputs$")
    public void submit_repayment_inputs() {
        repaymentCalculator.submitForm(repaymentsTestData);
    }
        //validates repayment interest, monthly repayment, total interest
    @Then("^Validate repayment calculations (.*)$")
    public void Validate_repayment_calculations(String fileName) {
         HashMap<String, String> actualRepaymentCalculations= repaymentCalculator.getRepaymentCalculations();
        repaymentsTestData = parseCSV("src/testData/"+fileName);
        Iterator it = actualRepaymentCalculations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            assertEquals(pair.getValue().toString(), repaymentsTestData.get(pair.getKey().toString()));
            it.remove();
        }
    }

    //closing browser
    @After
    public void closeBrowser() {
            driver.quit();
    }

}
