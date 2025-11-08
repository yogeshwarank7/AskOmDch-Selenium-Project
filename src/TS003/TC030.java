package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC030 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Open the homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Step 1: Opened AskOmDch homepage successfully.");

            // Step 3: Scroll to the “SHOP NOW” button
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 700);");
            Thread.sleep(1500);

            // Step 4: Click on “SHOP NOW”
            WebElement shopNowButton = driver.findElement(By.xpath("//a[contains(text(),'Shop Now')]"));
            shopNowButton.click();
            System.out.println("✅ Step 2: Clicked on 'SHOP NOW' button.");

            // Step 5: Wait 3 seconds for the page to load
            Thread.sleep(3000);

            // Step 6: Take screenshot and mark test as PASS
            captureScreenshot(driver, "TC030-Screenshot_Pass.png");
            System.out.println("📸 Screenshot taken for 'Shop Now' page - Marked as PASS ✅");

        } catch (Exception e) {
            System.out.println("❌ Exception occurred: " + e.getMessage());
            try {
                if (driver != null) {
                    captureScreenshot(driver, "TC030-Screenshot_Fail.png");
                    System.out.println("📸 Screenshot taken - Marked as FAIL ❌");
                }
            } catch (Exception ex) {
                System.out.println("⚠️ Unable to take screenshot: " + ex.getMessage());
            }

        } finally {
            // Step 7: Quit the browser fast
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            File destDir = new File(folderPath);
            if (!destDir.exists()) destDir.mkdirs();

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(destDir, fileName);
            FileHandler.copy(src, dest);

            System.out.println("📁 Screenshot saved at: " + dest.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Screenshot save failed: " + ioe.getMessage());
        }
    }
}
