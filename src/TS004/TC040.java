package TS004;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TC040 {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", 
            "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");
        ChromeDriver driver = null;

        try {
            // Step 1: Open browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("Navigated to homepage.");

            // Step 3: Locate product images
            List<WebElement> productImages = driver.findElements(By.cssSelector(".woocommerce ul.products li img"));

            if (productImages.isEmpty()) {
                throw new Exception("No product images found on the homepage.");
            }

            boolean allImagesLoaded = true;

            // Step 4: Validate each image
            for (WebElement img : productImages) {
                String src = img.getAttribute("src");
                String naturalWidth = img.getAttribute("naturalWidth");

                if (!img.isDisplayed() || naturalWidth == null || naturalWidth.equals("0")) {
                    System.out.println("❌ Broken or missing image: " + src);
                    allImagesLoaded = false;
                } else {
                    System.out.println("✅ Image loaded successfully: " + src);
                }
            }

            // Step 5: Final validation
            if (allImagesLoaded) {
                System.out.println("✅ [PASS] All product images load correctly without distortion.");

                // Take screenshot for PASS
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\TC040_Pass.png";
                FileUtils.copyFile(screenshot, new File(filePath));
                System.out.println("📸 Screenshot saved successfully at: " + filePath);
            } else {
                throw new Exception("❌ [FAIL] Some product images failed to load properly.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test case failed: " + e.getMessage());

            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\TC040_Fail.png";
                try {
                    FileUtils.copyFile(screenshot, new File(filePath));
                    System.out.println("📸 Screenshot saved at: " + filePath);
                } catch (IOException ioException) {
                    System.out.println("❌ Failed to save screenshot: " + ioException.getMessage());
                }
            }

        } finally {
            // Step 6: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }
}
