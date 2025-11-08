package TS002;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC019 {

    public static void main(String[] args) {

        WebDriver driver = null;
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String testName = "TC019-Screenshot-";

        try {
            // Set path to ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // Launch browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 1: Visit the website
            driver.get("https://askomdch.com/");

            // Step 2: Click the “FIND MORE” button
            WebElement findMoreButton = driver.findElement(By.xpath("//a[text()='Find More']"));
            findMoreButton.click();

            // Step 3: Verify navigation or content update
            Thread.sleep(3000); // Wait for navigation or section update

            String currentUrl = driver.getCurrentUrl().toLowerCase();

            // Expected behavior: Redirects to offers/info page or different section
            if (!currentUrl.equals("https://askomdch.com/")) {
                System.out.println("✅ Test Passed: Clicking 'FIND MORE' navigates to additional offer/information section.");
                System.out.println("Current URL: " + currentUrl);
                takeScreenshot(driver, screenshotPath + testName + "pass.png");
            } else {
                System.out.println("❌ Test Failed: 'FIND MORE' button did not navigate or update the page.");
                System.out.println("Current URL: " + currentUrl);
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred during test execution: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }
        } finally {
            // Ensure browser is closed
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // Screenshot helper method
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
