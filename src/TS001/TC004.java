package TS001;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TC004 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate to website
            driver.get("https://askomdch.com/");
            System.out.println("✅ Navigated to AskOmDch website.");

            // Step 3: Click on the "Store" link
            WebElement storeLink = driver.findElement(By.xpath("//a[text()='Store']"));
            storeLink.click();
            System.out.println("🛒 Clicked on 'Store' link.");

            // Step 4: Wait 3 seconds for page to load fully
            Thread.sleep(3000);

            // Step 5: Verify correct page loaded
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/store")) {
                System.out.println("✅ Store page loaded successfully: " + currentUrl);
                takeScreenshot(driver, "TC004_StorePage_Pass");
            } else {
                System.out.println("❌ Unexpected URL: " + currentUrl);
                takeScreenshot(driver, "TC004_StorePage_Fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, "TC004_Exception_Fail");
            }
        } finally {
            // Step 6: Close browser immediately after screenshot
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // Screenshot utility
    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            new File(folderPath).mkdirs();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File dest = new File(folderPath + fileName + "_" + timeStamp + ".png");
            FileUtils.copyFile(src, dest);
            System.out.println("📸 Screenshot saved successfully: " + dest.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }
}
