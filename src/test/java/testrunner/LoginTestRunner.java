package testrunner;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;

import java.util.List;

public class LoginTestRunner extends Setup {
    DashboardPage dashboardPage;
    LoginPage loginPage;
    @Test(priority = 1, description = "Admin log in successfully")
    public void doLogin(){
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
        String urlActual=driver.getCurrentUrl();
        String urlExpected="viewEmployeeList";
        Assert.assertTrue(urlActual.contains(urlExpected));
        Allure.description("Admin logged in successfully");
    }
    @Test(priority = 2, description = "Admin profile image showing")
    public void isProfileImageExists(){
        dashboardPage=new DashboardPage(driver);
//        WebElement imgProfile= driver.findElement(By.className("oxd-userdropdown-img"));
        Assert.assertTrue(dashboardPage.imgProfile.isDisplayed());
        Allure.description("Admin profile image is exists");
    }
    @Test(priority = 3, description = "Select employee status")
    public void selectEmploymentStatus() throws InterruptedException {
        dashboardPage.dropdowns.get(0).click();
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ENTER);
        dashboardPage.btnSubmit.click();
        Thread.sleep(3000);
        List<WebElement> txtData= driver.findElements(By.tagName("span"));
        String dataActual= txtData.get(14).getText();
        String dataExpected="Records Found";
        Assert.assertTrue(dataActual.contains(dataExpected));
        Allure.description("Data found");
    }
    @Test(priority = 4, description = "Showing employee list")
    public void listEmployee(){
        Utils.doScroll(driver);
        WebElement table= driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows= table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row:allRows) {
            List<WebElement> cells= row.findElements(By.cssSelector("[role=cell]"));
            System.out.println(cells.get(5).getText());
            Assert.assertTrue(cells.get(5).getText().contains("Full-Time Contract"));

        }
        Allure.description("Employee list showing properly");
    }
}
