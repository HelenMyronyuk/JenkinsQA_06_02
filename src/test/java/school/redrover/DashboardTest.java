package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardTest extends BaseTest {

    private static final String DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);
    private static final String NEW_DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify of the dashboard table size")
    @Test
    public void testDashboardTableSize() {
        Map<String, Integer> tableSizeMap = new LinkedHashMap<>();
        tableSizeMap.put("Small", 71);
        tableSizeMap.put("Medium", 86);
        tableSizeMap.put("Large", 102);

        List<Integer> sizeList = new ArrayList<>(tableSizeMap.values());
        List<Integer> tableSizeActualList = new ArrayList<>();

        TestUtils.createJob(this, "JOB", TestUtils.JobType.Pipeline, true);

        for (Map.Entry<String, Integer> tableSizeNameAndTableSizeMap : tableSizeMap.entrySet()) {
            tableSizeActualList.add(new MainPage(getDriver())
                    .clickChangeJenkinsTableSize(tableSizeNameAndTableSizeMap.getKey())
                    .getJenkinsTableHeight());
        }

        Assert.assertEquals(tableSizeActualList, sizeList);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of the presence of a description preview on the main page")
    @Test
    public void testPreviewDescriptionOnMainPage() {
        String preview = new MainPage(getDriver())
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(preview, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of adding of a description on the main page")
    @Test
    public void testAddDescriptionOnMainPage() {
        String textDescription = new MainPage(getDriver())
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of the presence of a description preview from My View page")
    @Test
    public void testPreviewDescriptionFromMyViewsPage() {
        String previewDescription = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(DESCRIPTION)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of adding of a description from My View page")
    @Test
    public void testAddDescriptionFromMyViewsPage() {
        String description = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verify of description field editing")
    @Test
    public void testEditDescription() {
        String description = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(description, NEW_DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify of icon and text displaying on Legend Icon page")
    @Test
    public void testIconLegend() {
        final List<String> iconDescriptionTextList = List.of("The last build was successful.",
                "The last build was successful. A new build is in progress.",
                "The last build was successful but unstable. This is primarily used to represent test failures.",
                "The last build was successful but unstable. A new build is in progress.",
                "The last build failed.",
                "The last build failed. A new build is in progress.",
                "The project has never been built.",
                "The first build is in progress.",
                "The project is disabled.",
                "The project is disabled, but a build is in progress.",
                "The last build was aborted.",
                "The last build was aborted. A new build is in progress.",
                "Project health is over 80%",
                "Project health is over 60% and up to 80%",
                "Project health is over 40% and up to 60%",
                "Project health is over 20% and up to 40%",
                "Project health is 20% or less");

        TestUtils.createJob(this, "NEW_JOB", TestUtils.JobType.FreestyleProject, true);

        List<String> actualIconDescriptionTextList = new MainPage(getDriver())
                .clickIconLegend()
                .getIconDescriptionTextList();

        Assert.assertEquals(actualIconDescriptionTextList, iconDescriptionTextList);
    }
}
