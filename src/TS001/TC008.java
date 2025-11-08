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

public class TC008 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Launch browser
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Open website
            driver.get("https://askomdch.com");

            // Step 3: Click on “Account” link in header
            driver.findElement(By.xpath("//a[normalize-space()='Account']")).click();

            // Step 4: Validate navigation
            String currentUrl = driver.getCurrentUrl();

            if (currentUrl.contains("/account") || currentUrl.contains("my-account")) {
                System.out.println("✅ Test Passed: Account/Login page opened successfully.");
                captureScreenshot(driver, "TC008-Screenshot-pass");
            } else {
                System.out.println("❌ Test Failed: Incorrect page opened. URL - " + currentUrl);
                captureScreenshot(driver, "TC008-Screenshot-fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception during test: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC008-Screenshot-fail");
            } else {
                System.out.println("⚠️ Screenshot skipped — driver not initialized.");
            }
        } finally {
            // Step 5: Ensure browser closes properly
            try {
                if (driver != null) {
                    Thread.sleep(2000); // Wait to ensure screenshot is saved
                    driver.quit();
                    System.out.println("Browser closed successfully.");
                }
            } catch (Exception closeErr) {
                System.out.println("⚠️ Error closing browser: " + closeErr.getMessage());
            }
        }
    }

    // ✅ Screenshot Utility Method
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            // Define screenshot directory
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            File destDir = new File(folderPath);

            // Create folder if missing
            if (!destDir.exists()) {
                boolean created = destDir.mkdirs();
                if (created) {
                    System.out.println("📁 Created screenshot directory at: " + destDir.getAbsolutePath());
                }
            }

            // ✅ Append ".png" to the filename properly
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(destDir, fileName + ".png");

            // ✅ Allow overwrite or new file creation
            FileHandler.copy(src, destFile);

            System.out.println("📸 Screenshot saved successfully at: " + destFile.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Screenshot saving failed: " + ioe.getMessage());
        }
    }
}
