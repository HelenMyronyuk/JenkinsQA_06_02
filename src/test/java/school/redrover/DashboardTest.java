package school.redrover;

import io.qameta.allure.Description;
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
}
