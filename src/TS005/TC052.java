package TS005;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;

public class TC052 {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            // Step 1: Open browser and navigate to homepage
            driver.get("https://askomdch.com/");
            driver.manage().window().maximize();

            // Step 2: Scroll to icon section
            // The section usually contains text like "Global Shipping", "Best Quality", etc.
            WebElement iconSection = driver.findElement(By.xpath("//div[contains(@class,'wp-block-columns')]"));

            // Verify if the section is displayed
            if (iconSection.isDisplayed()) {
                System.out.println("TC052 Passed: Icon and text section displayed correctly on the homepage.");

                // Capture screenshot for Passed case
                takeScreenshot(driver, "TC052-Screenshot-Pass");
            } else {
                System.out.println("TC052 Failed: Icon and text section is not visible on the homepage.");

                // Capture screenshot for Failed case
                takeScreenshot(driver, "TC052-Screenshot-Fail");
            }

        } catch (Exception e) {
            System.out.println("TC052 Failed due to Exception: " + e.getMessage());
            takeScreenshot(driver, "TC052-Screenshot-Fail");

        } finally {
            // Close browser after execution
            driver.quit();
        }
    }

    // Method to take a screenshot
    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, new File("C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\" + fileName + ".png"));
        } catch (IOException e) {
            System.out.println("Error while saving screenshot: " + e.getMessage());
        }
    }
}
