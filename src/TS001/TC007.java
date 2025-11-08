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

public class TC007 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Launch browser
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Navigate to the site
            driver.get("https://askomdch.com");

            // Step 3: Click on “Accessories” link
            driver.findElement(By.xpath("//a[normalize-space()='Accessories']")).click();

            // Step 4: Verify navigation
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/accessories")) {
                System.out.println("✅ Test Passed: Accessories page opened successfully.");
                captureScreenshot(driver, "TC007-Screenshot-pass.png");
            } else {
                System.out.println("❌ Test Failed: Incorrect page opened. URL: " + currentUrl);
                captureScreenshot(driver, "TC007-Screenshot-fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception during test: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC007-Screenshot-fail.png");
            } else {
                System.out.println("⚠️ Screenshot skipped — driver not initialized.");
            }
        } finally {
            // Step 5: Close the browser safely
            try {
                if (driver != null) {
                    Thread.sleep(2000); // ensure screenshot writes before closing
                    driver.quit();
                    System.out.println("Browser closed successfully.");
                }
            } catch (Exception closeErr) {
                System.out.println("⚠️ Error closing browser: " + closeErr.getMessage());
            }
        }
    }

    // ✅ Screenshot Utility (Absolute Path + Folder Check)
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            // Absolute screenshot folder path
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            File destDir = new File(folderPath);

            if (!destDir.exists()) {
                boolean created = destDir.mkdirs();
                if (created) {
                    System.out.println("📁 Created screenshot directory at: " + destDir.getAbsolutePath());
                }
            }

            // Capture and save
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(destDir, fileName);
            FileHandler.copy(src, dest);

            System.out.println("📸 Screenshot saved successfully at: " + dest.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Screenshot saving failed: " + ioe.getMessage());
        }
    }
}
