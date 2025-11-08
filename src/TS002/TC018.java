package TS002;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC018 {

    public static void main(String[] args) {

        WebDriver driver = null;
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String testName = "TC018-Screenshot-";

        try {
            // Set ChromeDriver path (update path if needed)
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // Launch Chrome browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 1: Open website
            driver.get("https://askomdch.com/");

            // Step 2: Click on “SHOP NOW”
            WebElement shopNowButton = driver.findElement(By.xpath("//a[text()='Shop Now']"));
            shopNowButton.click();

            // Step 3: Verify navigation
            Thread.sleep(3000); // Wait for navigation

            String currentUrl = driver.getCurrentUrl().toLowerCase();
            String expectedUrlPart = "store";

            if (currentUrl.contains(expectedUrlPart)) {
                System.out.println("✅ Test Passed: Clicking 'SHOP NOW' redirects to Store/Product page.");
                takeScreenshot(driver, screenshotPath + testName + "pass.png");
            } else {
                System.out.println("❌ Test Failed: 'SHOP NOW' did not navigate to Store/Product page.");
                System.out.println("Current URL: " + currentUrl);
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred during test execution: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }
        } finally {
            // Always close the browser
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // Utility for screenshots
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
