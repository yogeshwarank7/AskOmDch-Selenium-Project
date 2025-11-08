package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TC026 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Open the website
            driver.get("https://askomdch.com/");
            System.out.println("✅ Opened https://askomdch.com/ successfully.");

            // Step 3: Scroll to promotional section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(2000);

            // Step 4: Locate promotional banner section (20% Off, Eyewear, Suit Up)
            List<WebElement> banners = driver.findElements(
                    By.xpath("//img[contains(@src,'promo') or contains(@src,'banner') or contains(@src,'20')]")
            );

            // Wait 3 seconds before validation
            Thread.sleep(3000);

            if (banners.size() == 0) {
                System.out.println("❌ No promotional banner images found!");
                captureScreenshot(driver, "TC026-Screenshot-Fail.png");
                return;
            }

            boolean allLoaded = true;

            // Step 5: Check if each banner image is loaded properly
            for (WebElement banner : banners) {
                boolean loaded = (Boolean) js.executeScript(
                        "return arguments[0].complete && arguments[0].naturalWidth > 0", banner);

                if (!loaded) {
                    allLoaded = false;
                    System.out.println("❌ Banner failed to load: " + banner.getAttribute("src"));
                }
            }

            // Step 6: Take screenshot only of promotional section
            WebElement promoSection = banners.get(0).findElement(By.xpath("ancestor::div[contains(@class,'wp-block-columns')]"));
            File src = promoSection.getScreenshotAs(OutputType.FILE);

            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            new File(folderPath).mkdirs();

            File dest;
            if (allLoaded) {
                System.out.println("✅ Test Passed: All promotional banners are loading correctly.");
                dest = new File(folderPath, "TC026-Screenshot-Pass.png");
            } else {
                System.out.println("❌ Test Failed: One or more promotional banners not loaded properly.");
                dest = new File(folderPath, "TC026-Screenshot-Fail.png");
            }

            FileHandler.copy(src, dest);
            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("⚠️ Exception during test: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC026-Screenshot-Fail.png");
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // Utility: Capture full-page screenshot (fallback)
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
            new File(folderPath).mkdirs();

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(folderPath, fileName);
            FileHandler.copy(src, dest);

            System.out.println("📸 Screenshot saved successfully at: " + dest.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Failed to save screenshot: " + ioe.getMessage());
        }
    }
}
