package TS002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC016 {

    public static void main(String[] args) {
        WebDriver driver = null;
        String screenshotName = "TC016-Screenshot";

        try {
            // ✅ Setup ChromeDriver (update the path as per your system)
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // ✅ Step 1: Open the website
            driver.get("https://askomdch.com/");

            // ✅ Step 2: Scroll to the banner section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 500);"); // scroll down slightly to ensure visibility

            // ✅ Step 3: Locate the “SHOP NOW” button
            WebElement shopNowButton = driver.findElement(By.xpath("//a[contains(text(),'Shop Now') or contains(text(),'SHOP NOW')]"));

            // ✅ Step 4: Validate visibility
            if (shopNowButton.isDisplayed()) {
                System.out.println("✅ Test Passed: 'SHOP NOW' button is visible below the offer text.");
                captureScreenshot(driver, screenshotName + "-pass.png");
            } else {
                System.out.println("❌ Test Failed: 'SHOP NOW' button is not visible.");
                captureScreenshot(driver, screenshotName + "-fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());

            // ✅ Capture FAIL screenshot if exception occurs
            if (driver != null) {
                captureScreenshot(driver, screenshotName + "-fail.png");
            }

        } finally {
            // ✅ Always close the browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed successfully.");
            }
        }
    }

    // 📸 Utility method to capture screenshots
    private static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            File dir = new File(screenshotPath);
            if (!dir.exists()) {
                dir.mkdirs(); // create directory if missing
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath + fileName);
            FileHandler.copy(screenshot, destFile);

            System.out.println("📸 Screenshot saved as: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }
}
