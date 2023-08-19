package school.redrover.runner;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.api.*;

public class JenkinsUtils {

    @Step("Delete Jobs")
    private static void deleteJobs(JenkinsSpecBuilder specBuilder) {
        var jobsService = new JobService(specBuilder);
        jobsService.getJobs().forEach(j -> jobsService.deleteJob(j).then().statusCode(302));
    }

    @Step("Delete Views")
    private static void deleteViews(JenkinsSpecBuilder specBuilder) {
        var viewService = new ViewService(specBuilder);
        viewService.getViews()
                .stream()
                .filter(v -> !v.equalsIgnoreCase("all"))
                .forEach(v -> viewService.deleteView(v).then().statusCode(302));

        var myViewService = new MyViewService(specBuilder);
        myViewService.getMyViews()
                .stream()
                .filter(v -> !v.equalsIgnoreCase("all"))
                .forEach(v -> myViewService.deleteMyView(v).then().statusCode(302));
    }

    @Step("Delete Users")
    private static void deleteUsers(JenkinsSpecBuilder specBuilder) {
        var userService = new UserService(specBuilder);
        userService.getUsers()
                .stream()
                .filter(u -> !UserService.DEFAULT_USERS.contains(u))
                .forEach(u -> userService.deleteUser(u).then().statusCode(302));
    }

    @Step("Delete Nodes")
    private static void deleteNodes(JenkinsSpecBuilder specBuilder) {
        var nodeService = new NodeService(specBuilder);
        nodeService.getNodes()
                .stream()
                .filter(u -> !NodeService.DEFAULT_NODES.contains(u))
                .forEach(u -> nodeService.deleteNode(u).then().statusCode(302));
    }


    @Step("Delete description")
    private static void deleteDescription(JenkinsSpecBuilder specBuilder) {
        var baseService = new BaseService(specBuilder);
        baseService.submitDescription("").then().statusCode(302);
    }

    @Step("Clear Data for Jenkins instance {0}")
    static void clearData(JenkinsSpecBuilder specBuilder) {
        JenkinsUtils.deleteViews(specBuilder);
        JenkinsUtils.deleteJobs(specBuilder);
        JenkinsUtils.deleteUsers(specBuilder);
        JenkinsUtils.deleteNodes(specBuilder);
        JenkinsUtils.deleteDescription(specBuilder);
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

