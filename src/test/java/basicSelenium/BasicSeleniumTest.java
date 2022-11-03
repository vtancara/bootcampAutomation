package basicSelenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Keys;

import java.util.Date;

public class BasicSeleniumTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://todo.ly/");
    }

    @AfterEach
    public void cleanup(){
        driver.quit();
    }

    @Test
    public void verifyCRUDProject() throws InterruptedException {

        // login
        driver.findElement(By.xpath("//img[contains(@src,'pagelogin')]")).click();
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("bootcamp@mojix44.com");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("12345");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();
        Thread.sleep(1000);
        Assertions.assertTrue(driver.findElement(By.id("ctl00_HeaderTopControl1_LinkButtonLogout")).isDisplayed()
                                    ,"ERROR login was incorrect");

        // create
        String nameProject="Veronica-Project"+new Date().getTime();
        driver.findElement(By.xpath("//td[text()='Add New Project']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);
        Thread.sleep(1000);
        driver.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(1000);
        int actualResult=driver.findElements(By.xpath(" //td[text()='"+nameProject+"'] ")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The project was not created");

        // create task
        String nameTask="MI TAREITA 1";
        driver.findElement(By.id("NewItemContentInput")).sendKeys(nameTask);
        driver.findElement(By.id("NewItemAddButton")).click();
        Thread.sleep(1000);
        System.out.println(driver.findElements(By.xpath("//td/div[text()='"+nameTask+"']")));
        actualResult=driver.findElements(By.xpath("//div[text()='"+nameTask+"']")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The TASK was not created");



        // update task
        driver.findElement(By.xpath("//td[contains(@class,'ItemContent')]//div[text()='"+nameTask+"']")).click();
        nameTask=nameTask + new Date().getTime();
        driver.findElement(By.xpath("//td//div/textarea[@id='ItemEditTextbox']")).clear();
        driver.findElement(By.xpath("//td//div/textarea[@id='ItemEditTextbox']")).sendKeys(nameTask);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//td//div/textarea[@id='ItemEditTextbox']")).sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        actualResult=driver.findElements(By.xpath(" //td[text()='"+nameTask+"'] ")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The TASK was not updated");
    }
}
