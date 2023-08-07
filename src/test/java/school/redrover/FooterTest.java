package school.redrover;

import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification that the link on the footer contains 'Jenkins 2.387.2")
    @Test
    public void testJenkinsVersion() {
        String linkVersion = new MainPage(getDriver())
                .getHeader()
                .getLinkVersion();

        Assert.assertEquals(linkVersion, "Jenkins 2.387.2");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification that the link redirect to the 'Jenkins' page " +
            "and getting title from the Jenkins page'")
    @Test
    public void testLinkJenkinsVersion() {
        String jenkinsText = new MainPage(getDriver())
                .clickJenkinsVersionLink()
                .switchJenkinsDocPage()
                .getJenkinsPageTitle();

        Assert.assertEquals(jenkinsText, "Jenkins");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verification that the link on the footer redirect to the 'API' page")
    @Test
    public void testLinkRestApi() {
        String mainPage = new MainPage(getDriver())
                .getHeader()
                .clickOnRestApiLink()
                .getRestApiPageTitle();

        Assert.assertEquals(mainPage, "REST API");
    }
}
