package TS002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC015 {

    public static void main(String[] args) {
        WebDriver driver = null;
        String screenshotName = "TC015-Screenshot";

        try {
            // ✅ Setup ChromeDriver (update the path as per your local setup)
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // ✅ Step 1: Open the website
            driver.get("https://askomdch.com/");

            // ✅ Step 2: Locate the subtext below the main heading
            WebElement subText = driver.findElement(By.xpath("//*[contains(text(),'25% OFF On all products')]"));

            // ✅ Step 3: Validate visibility and text
            String expectedText = "25% OFF On all products";
            String actualText = subText.getText().trim();

            if (subText.isDisplayed() && actualText.equals(expectedText)) {
                System.out.println("✅ Test Passed: Subtext is visible and correct.");
                captureScreenshot(driver, screenshotName + "-pass.png");
            } else {
                System.out.println("❌ Test Failed: Text mismatch or not visible. Found: " + actualText);
                captureScreenshot(driver, screenshotName + "-fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());

            // ✅ Capture fail screenshot on exception
            if (driver != null) {
                captureScreenshot(driver, screenshotName + "-fail.png");
            }

        } finally {
            // ✅ Always close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed successfully.");
            }
        }
    }

    // 📸 Utility method for saving screenshots
    private static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            File dir = new File(screenshotPath);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if missing
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath + fileName);
            FileHandler.copy(screenshot, destFile);

            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }
}
