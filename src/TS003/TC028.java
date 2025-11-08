package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TC028 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Navigate to the homepage
            driver.get("https://askomdch.com/");
            System.out.println("Opened AskOmDch homepage successfully.");

            // Step 3: Scroll to banner section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 600);");
            Thread.sleep(2000);

            // Step 4: Find all “SHOP NOW” buttons under banners
            List<WebElement> shopNowButtons = driver.findElements(By.xpath("//a[contains(text(),'Shop Now')]"));

            // Step 5: Validate that at least two “SHOP NOW” buttons are present
            if (shopNowButtons.size() >= 2) {
                System.out.println("✅ Test Passed: 'SHOP NOW' button is visible under both banners.");
                captureScreenshot(driver, "TC028-Screenshot-pass.png");
            } else {
                System.out.println("❌ Test Failed: 'SHOP NOW' button missing under one or more banners.");
                captureScreenshot(driver, "TC028-Screenshot-fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC028-Screenshot-fail.png");
            }
        } finally {
            // Step 6: Close the browser
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }

    // Screenshot utility method
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            File destDir = new File(folderPath);

            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(destDir, fileName);
            FileHandler.copy(src, dest);

            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Failed to save screenshot: " + ioe.getMessage());
        }
    }
}
