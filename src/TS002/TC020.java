package TS002;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

public class TC020 {

    public static void main(String[] args) {

        WebDriver driver = null;
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String testName = "TC020-Screenshot-";

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Open the website
            driver.get("https://askomdch.com/");

            // Step 3: Locate both buttons
            WebElement shopNowButton = driver.findElement(By.xpath("//a[text()='Shop Now']"));
            WebElement findMoreButton = driver.findElement(By.xpath("//a[text()='Find More']"));

            // Step 4: Get initial style properties (color or background)
            String shopNowBefore = shopNowButton.getCssValue("background-color");
            String findMoreBefore = findMoreButton.getCssValue("background-color");

            // Step 5: Hover over buttons
            Actions actions = new Actions(driver);
            actions.moveToElement(shopNowButton).perform();
            Thread.sleep(1000); // Allow hover effect to apply

            String shopNowAfter = shopNowButton.getCssValue("background-color");

            actions.moveToElement(findMoreButton).perform();
            Thread.sleep(1000);

            String findMoreAfter = findMoreButton.getCssValue("background-color");

            // Step 6: Validate hover effect
            boolean shopNowChanged = !shopNowBefore.equals(shopNowAfter);
            boolean findMoreChanged = !findMoreBefore.equals(findMoreAfter);

            if (shopNowChanged && findMoreChanged) {
                System.out.println("✅ Test Passed: Hover effects visible on both buttons.");
                System.out.println("SHOP NOW - Before: " + shopNowBefore + ", After: " + shopNowAfter);
                System.out.println("FIND MORE - Before: " + findMoreBefore + ", After: " + findMoreAfter);
                takeScreenshot(driver, screenshotPath + testName + "pass.png");
            } else {
                System.out.println("❌ Test Failed: Hover effect not detected on one or both buttons.");
                System.out.println("SHOP NOW - Before: " + shopNowBefore + ", After: " + shopNowAfter);
                System.out.println("FIND MORE - Before: " + findMoreBefore + ", After: " + findMoreAfter);
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred during test execution: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, screenshotPath + testName + "fail.png");
            }
        } finally {
            // Step 7: Close browser always
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
