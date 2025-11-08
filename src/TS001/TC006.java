package TS001;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TC006 {

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Step 1: Launch browser
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Navigate to site
            driver.get("https://askomdch.com");
            System.out.println("Navigated to AskOmDch website.");

            // Step 3: Click on “Women” link
            driver.findElement(By.xpath("//a[normalize-space()='Women']")).click();
            System.out.println("Clicked on 'Women' link.");

            // Step 4: Verify redirection
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/women")) {
                System.out.println("✅ Test Passed: Women category page displayed successfully.");
                captureScreenshot(driver, "Pass");
            } else {
                System.out.println("❌ Test Failed: Incorrect page opened. URL - " + currentUrl);
                captureScreenshot(driver, "Fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception during test: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "Fail");
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility Method
    public static void captureScreenshot(WebDriver driver, String status) {
        try {
            // Folder path
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // File name with timestamp
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "TC006-Screenshot-" + status + "-" + timeStamp + ".png";

            // Take screenshot and save
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(folder, fileName);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📸 Screenshot saved successfully at: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Screenshot capture failed: " + e.getMessage());
        }
    }
}
