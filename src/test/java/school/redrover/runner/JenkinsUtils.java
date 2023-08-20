package school.redrover.runner;

import io.qameta.allure.Step;
import io.restassured.filter.log.LogDetail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.JenkinsInstance;
import school.redrover.api.*;

public class JenkinsUtils {

    @Step("Delete Jobs from {0}")
    private static void deleteJobs(JenkinsInstance instance) {
        var jobsService = new JobService(instance);
        jobsService.getJobUrls().forEach(ju -> jobsService.deleteJobByUrl(ju).then().log().ifValidationFails(LogDetail.BODY).statusCode(302));
    }

    @Step("Delete Views from {0}")
    private static void deleteViews(JenkinsInstance instance) {
        var viewService = new ViewService(instance);
        viewService.getViews()
                .stream()
                .filter(v -> !v.equalsIgnoreCase("all"))
                .forEach(v -> viewService.deleteView(v).then().log().ifValidationFails(LogDetail.BODY).statusCode(302));

        var myViewService = new MyViewService(instance);
        myViewService.getMyViews()
                .stream()
                .filter(v -> !v.equalsIgnoreCase("all"))
                .forEach(mv -> myViewService.deleteMyView(mv).then().log().ifValidationFails(LogDetail.BODY).statusCode(302));
    }

    @Step("Delete Users from {0}")
    private static void deleteUsers(JenkinsInstance instance) {
        var userService = new UserService(instance);
        userService.getUsers()
                .stream()
                .filter(u -> !UserService.DEFAULT_USERS.contains(u))
                .forEach(u -> userService.deleteUser(u).then().log().ifValidationFails(LogDetail.BODY).statusCode(302));
    }

    @Step("Delete Nodes from {0}")
    private static void deleteNodes(JenkinsInstance instance) {
        var nodeService = new NodeService(instance);
        nodeService.getNodes()
                .stream()
                .filter(u -> !NodeService.DEFAULT_NODES.contains(u))
                .forEach(u -> nodeService.deleteNode(u).then().log().ifValidationFails(LogDetail.BODY).statusCode(302));
    }


    @Step("Delete description from {0}")
    private static void deleteDescription(JenkinsInstance instance) {
        var baseService = new BaseService(instance);
        baseService.submitDescription("").then().log().ifValidationFails(LogDetail.BODY).statusCode(302);
    }

    @Step("Clear Data for Jenkins instance {0}")
    static synchronized void clearData(JenkinsInstance instance) {
        JenkinsUtils.deleteViews(instance);
        JenkinsUtils.deleteJobs(instance);
        JenkinsUtils.deleteUsers(instance);
        JenkinsUtils.deleteNodes(instance);
        JenkinsUtils.deleteDescription(instance);
    }

    static void login(WebDriver driver) {
        driver.findElement(By.name("j_username")).sendKeys(ProjectUtils.getUserName());
        driver.findElement(By.name("j_password")).sendKeys(ProjectUtils.getPassword());
        driver.findElement(By.name("Submit")).click();
    }

    @Step("Logout from instance {1}")
    static void logout(WebDriver driver, String url) {
        ProjectUtils.get1(driver, url);

        driver.findElement(By.xpath("//a[@href='/logout']")).click();
    }
}

