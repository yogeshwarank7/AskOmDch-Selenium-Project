package TS002;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC017 {

    public static void main(String[] args) {

        WebDriver driver = null;

        // Screenshot save path
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String testName = "TC017-Screenshot-";

        try {
            // Set ChromeDriver path
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // Launch browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 1: Open website
            driver.get("https://askomdch.com/");

            // Step 2: Find elements
            WebElement shopNowButton = driver.findElement(By.xpath("//a[text()='Shop Now']"));
            WebElement findMoreButton = driver.findElement(By.xpath("//a[text()='Find More']"));

            // Step 3: Verify that “FIND MORE” button is displayed next to “SHOP NOW”
            boolean isFindMoreDisplayed = findMoreButton.isDisplayed();

            Point shopNowLocation = shopNowButton.getLocation();
            Point findMoreLocation = findMoreButton.getLocation();

            // Alignment check (rough horizontal alignment)
            boolean isAligned = Math.abs(shopNowLocation.getY() - findMoreLocation.getY()) < 20;
            boolean isNextTo = findMoreLocation.getX() > shopNowLocation.getX();

            if (isFindMoreDisplayed && isAligned && isNextTo) {
                System.out.println("✅ Test Passed: 'FIND MORE' button is displayed next to 'SHOP NOW'.");
                takeScreenshot(driver, screenshotPath + testName + "pass.png");
            } else {
                System.out.println("❌ Test Failed: 'FIND MORE' button not correctly positioned.");
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred during test execution: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }
        } finally {
            // Always close browser
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // Screenshot utility
    public static void takeScreenshot(WebDriver driver, String filePath) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, new File(filePath));
            System.out.println("📸 Screenshot saved: " + filePath);
        } catch (IOException e) {
            System.out.println("Unable to save screenshot: " + e.getMessage());
        }
    }
}
