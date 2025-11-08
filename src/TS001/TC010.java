package TS001;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC010 {

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            // Step 1: Open browser and navigate to the site
            driver.get("https://askomdch.com");

            // Step 2: Click on “Contact Us” link in the header
            driver.findElement(By.linkText("Contact Us")).click();

            // Step 3: Verify Contact page is opened
            boolean isContactPageDisplayed = driver.getTitle().toLowerCase().contains("contact")
                    || driver.getCurrentUrl().contains("contact");

            if (isContactPageDisplayed) {
                System.out.println("Test Passed: Contact page opened successfully.");
                takeScreenshot(driver, "TC010-Screenshot-pass");
            } else {
                System.out.println("Test Failed: Contact page did not open.");
                takeScreenshot(driver, "TC010-Screenshot-fail");
            }

        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, "TC010-Screenshot-fail");
            }
        } finally {
            // Ensure browser closes after execution
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            // Set destination folder for screenshots
            String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                    + fileName + ".png";

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileHandler.copy(srcFile, destFile);

            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving screenshot: " + e.getMessage());
        }
    }
}
