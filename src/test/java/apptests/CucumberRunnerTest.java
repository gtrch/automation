package apptests;

import static io.cucumber.junit.platform.engine.Constants.*;

import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "apptests")

@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, json:target/cucumber-report.json, html:reports/cucumber-report.html"
)

@ConfigurationParameter(key = "cucumber.execution.verbose", value = "true")
public class CucumberRunnerTest {
}