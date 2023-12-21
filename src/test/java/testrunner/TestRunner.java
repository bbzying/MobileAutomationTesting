package testrunner;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({"testsuite"})
@IncludeTags("smoketest")
@Suite
public class TestRunner {

}
