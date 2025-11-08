package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC029 {

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

            // Step 3: Scroll down to the last banner
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 900);");
            Thread.sleep(2000);

            // Step 4: Locate “CHECK OUT” button in the last banner
            WebElement checkOutButton = driver.findElement(By.xpath("//a[contains(text(),'Check Out')]"));

            if (checkOutButton.isDisplayed()) {
                System.out.println("✅ Test Passed: 'CHECK OUT' button is visible under the last banner.");
                captureScreenshot(driver, "TC029-Screenshot-pass.png");
            } else {
                System.out.println("❌ Test Failed: 'CHECK OUT' button not visible under the last banner.");
                captureScreenshot(driver, "TC029-Screenshot-fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC029-Screenshot-fail.png");
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }

    // Utility: Capture screenshot
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
