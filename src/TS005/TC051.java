package TS005;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class TC051 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Set ChromeDriver path
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // Step 2: Launch Chrome browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 3: Navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Homepage loaded successfully.");

            // Step 4: Verify Icon and Text Section (Global Shipping, Best Quality, etc.)
            java.util.List<WebElement> iconSections = driver.findElements(
                    By.cssSelector(".wp-block-columns .wp-block-column"));

            if (iconSections.size() >= 4) {
                System.out.println("✅ Found Icon & Text section with " + iconSections.size() + " items.");

                for (WebElement section : iconSections) {
                    String text = section.getText().trim();
                    System.out.println("🟢 Section Text: " + text);
                }

                System.out.println("🎉 TS051 PASS: Homepage and Icon/Text sections loaded correctly.");
                takeScreenshot(driver, "TC051-Screenshot-Pass");

            } else {
                throw new Exception("❌ Icon and Text sections not found properly. Found: " + iconSections.size());
            }

        } catch (Exception e) {
            System.out.println("⚠️ TS051 FAILED: " + e.getMessage());
            takeScreenshot(driver, "TC051-Screenshot-Fail");

        } finally {
            // Step 5: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Method
    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\" 
                            + fileName + ".png";
            FileUtils.copyFile(src, new File(filePath));
            System.out.println("📸 Screenshot saved at: " + filePath);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to capture screenshot: " + e.getMessage());
        }
    }
}
