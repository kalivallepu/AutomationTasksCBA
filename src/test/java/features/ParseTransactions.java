package features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utils.FileParser.parseFile;
import static utils.FileParser.writeListOfHasMapsToCSV;


/**
 * The ParseTransaction Step definition implements cucumber scripted scenarios.
 * This Step definition class verifies if file exists, parse and generates CSV.
 *
 * @author Kali Vallepu
 * @version 1.0.0
 * @since 2020-09-25
 */
public class ParseTransactions {

    @Given("^File Exists (.*)$")
    public void validate_file(String fileName) throws Throwable {
        File file = new File("src/testData/" + fileName);
        assertTrue(file.exists() && !file.isDirectory());
    }

    @Then("^Parse and Generate CSV (.*)$")
    public void parse_and_generate_csv(String fileName) throws Throwable {
        List<Map> listOfHashMaps = parseFile("src/testData/" + fileName);
        writeListOfHasMapsToCSV(listOfHashMaps);
        assertFalse(listOfHashMaps.isEmpty());
    }
}

