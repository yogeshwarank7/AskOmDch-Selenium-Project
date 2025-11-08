package TS001;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TC005 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate to website
            driver.get("https://askomdch.com");
            System.out.println("Navigated to https://askomdch.com");

            // Step 3: Click on "Men" link
            WebElement menLink = driver.findElement(By.xpath("//a[text()='Men']"));
            menLink.click();
            System.out.println("Clicked on the 'Men' link.");

            // Step 4: Verify redirection
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/men")) {
                System.out.println("✅ Test Passed: 'Men' category link redirects correctly.");
                takeScreenshot(driver, "Pass");
            } else {
                throw new Exception("❌ Test Failed: Navigation incorrect. Current URL: " + currentUrl);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());
            System.out.println("❌ Test Failed — capturing screenshot...");
            takeScreenshot(driver, "Fail");

        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility Method
    private static void takeScreenshot(WebDriver driver, String resultStatus) {
        try {
            if (driver != null) {
                // Folder to save screenshot
                String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                    System.out.println("📁 Folder created: " + folderPath);
                }

                // File name with timestamp
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "TC005_Screenshot_" + resultStatus + "_" + timeStamp + ".png";
                File destFile = new File(folderPath + File.separator + fileName);

                // Take screenshot
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Save the screenshot
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
            }
        } catch (IOException ex) {
            System.out.println("⚠️ Screenshot saving failed: " + ex.getMessage());
        }
    }
}
