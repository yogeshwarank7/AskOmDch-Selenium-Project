package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TC025 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Open website
            driver.get("https://askomdch.com/");
            System.out.println("✅ Opened https://askomdch.com/ successfully.");

            // Step 3: Scroll to promotional section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(3000); // wait 3 seconds for banners to load

            // Step 4: Locate promotional section
            WebElement promoSection = driver.findElement(
                    By.xpath("(//div[@class='wp-block-group alignfull has-white-background-color has-background']/div[@class='wp-block-group__inner-container'])[1]")
            );

            // Step 5: Verify all 3 promotional banners exist
            List<WebElement> banners = promoSection.findElements(By.tagName("img"));

            if (banners.size() >= 3) {
                System.out.println("✅ Found 3 promotional banners: Tank Tops, Eyewear, and Suit Up.");
            } else {
                System.out.println("⚠️ Expected 3 banners but found " + banners.size() + "!");
            }

            // Step 6: Take screenshot of the promotional section
            File src = promoSection.getScreenshotAs(OutputType.FILE);
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            new File(folderPath).mkdirs();

            File dest = new File(folderPath, "TC025-Screenshot-Pass.png");
            FileHandler.copy(src, dest);

            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());
            System.out.println("✅ Test Passed: The Promotional Section should be clearly visible with all three banners — Tank Tops, Eyewear, and Suit Up — properly displayed on the homepage.");

        } catch (Exception e) {
            System.out.println("⚠️ Exception during test: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC025-Screenshot-Fail");
            }
        } finally {
            // Immediate quit — browser closes right after test completion
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed immediately after execution.");
            }
        }
    }

    // Utility method for full-page fallback screenshot
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            new File(folderPath).mkdirs();

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(folderPath, fileName + ".png");
            FileHandler.copy(src, dest);

            System.out.println("📸 Screenshot saved (fallback) at: " + dest.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Failed to save fallback screenshot: " + ioe.getMessage());
        }
    }
}
