package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.model.ViewPage;
import school.redrover.model.jobs.FolderPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class DashboardTest extends BaseTest {

    private static final String PROJECT_NAME = RandomStringUtils.randomAlphanumeric(7);
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final String DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);
    private static final String NEW_DESCRIPTION = RandomStringUtils.randomAlphanumeric(7);

    @Test
    public void testDashboardTableSize() {
        Map<String, Integer> tableSizeMap = TestUtils.getJenkinsTableSizeMap();

        List<Integer> sizeList = new ArrayList<>(tableSizeMap.values());
        List<Integer> tableSizeActualList = new ArrayList<>();

        TestUtils.createJob(this, "JOB", TestUtils.JobType.Pipeline, true);

        for (Map.Entry<String, Integer> tableSizeNameAndTableSizeMap: tableSizeMap.entrySet()) {
            tableSizeActualList.add(new MainPage(getDriver())
                    .clickChangeJenkinsTableSize(tableSizeNameAndTableSizeMap.getKey())
                    .getJenkinsTableHeight());
        }

        Assert.assertEquals(tableSizeActualList, sizeList);
    }

    @Test
    public void testAddDescriptionOnMainPage(){
        String textDescription = new MainPage(getDriver())
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }

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

    @Test
    public void testCreateMyViewInFolder() {
        TestUtils.createJob(this, PROJECT_NAME, TestUtils.JobType.Folder, true);

        String newView = new MainPage(getDriver())
                .clickJobName(PROJECT_NAME, new FolderPage(getDriver()))
                .clickNewView()
                .setNewViewName(VIEW_NAME)
                .selectTypeViewClickCreate(TestUtils.ViewType.MyView, ViewPage.class)
                .getActiveViewName();

        assertEquals(newView, VIEW_NAME);
    }
}
