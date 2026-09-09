package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import utils.BeforeSuite;
import utils.DataToFeature;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "utils", "hooks"},
        snippets = SnippetType.CAMELCASE,
<<<<<<< HEAD
        tags = "@EXUS_HOG_WSP_19"
=======
        tags = "@Whatsapp_Post_33"
>>>>>>> fd01c6133c3dd9b48e20532a9a8a42798f06c9c9
)


@RunWith(CustomRunner.class)
public class GeneralRunner {
  @BeforeSuite
  public static void test() throws InvalidFormatException, IOException {
    DataToFeature.overrideFeatureFiles("src/test/resources/features");
  }
}
