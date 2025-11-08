package TS001;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class TC003 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate
            driver.get("https://askomdch.com");
            System.out.println("Navigated to https://askomdch.com");

            // Step 3: Click Home link
            WebElement homeLink = driver.findElement(By.xpath("//a[text()='Home']"));
            homeLink.click();
            System.out.println("Clicked on 'Home' link");

            // Step 4: Validate
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("https://askomdch.com/")) {
                System.out.println("✅ Test Passed: Navigation works.");
            } else {
                throw new Exception("❌ Navigation failed. Current URL: " + currentUrl);
            }

            // ✅ Take screenshot even if test passes
            takeScreenshot(driver, "TC003_Passed");

        } catch (Exception e) {
            System.out.println("⚠️ Exception: " + e.getMessage());
            System.out.println("Capturing screenshot...");
            takeScreenshot(driver, "TC003_Failed");

        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot method (Reusable)
    private static void takeScreenshot(WebDriver driver, String nameTag) {
        try {
            // Small delay
            Thread.sleep(1000);

            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Correct folder path
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (created) {
                    System.out.println("📁 Folder created: " + folderPath);
                } else {
                    System.out.println("⚠️ Failed to create folder: " + folderPath);
                }
            }

            // Add timestamp
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destFile = new File(folder, nameTag + "_" + timeStamp + ".png");

            // Save file
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());

        } catch (Exception ex) {
            System.out.println("⚠️ Screenshot failed: " + ex.getMessage());
        }
    }
}
