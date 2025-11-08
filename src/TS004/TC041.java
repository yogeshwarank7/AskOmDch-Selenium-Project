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

public class TC041 {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",
            "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        ChromeDriver driver = null;

        try {
            // Step 1: Open browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            System.out.println("✅ Browser launched successfully.");

            // Step 2: Navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Navigated to homepage.");

            // Step 3: Locate products with “Sale!” tags
            List<WebElement> saleTags = driver.findElements(By.xpath("//span[contains(text(),'Sale!')]"));

            // Step 4: Validation logic
            if (saleTags.isEmpty()) {
                throw new Exception("❌ No 'Sale!' tags found on any products.");
            } else {
                System.out.println("✅ 'Sale!' tags found on " + saleTags.size() + " products.");

                for (WebElement tag : saleTags) {
                    if (tag.isDisplayed()) {
                        System.out.println("🎯 'Sale!' tag visible on product: " + tag.getText());
                    } else {
                        throw new Exception("❌ 'Sale!' tag not visible for one of the discounted products.");
                    }
                }
                System.out.println("✅ [PASS] 'Sale!' tags appear correctly on discounted products.");

                // Take PASS screenshot
                takeScreenshot(driver, "TC041_Pass");
            }

        } catch (Exception e) {
            System.out.println("⚠️ [FAIL] Test case failed: " + e.getMessage());

            // Take FAIL screenshot
            if (driver != null) {
                try {
                    takeScreenshot(driver, "TC041_Fail");
                } catch (IOException ioEx) {
                    System.out.println("❌ Failed to save screenshot: " + ioEx.getMessage());
                }
            }

        } finally {
            // Step 5: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility Method
    public static void takeScreenshot(ChromeDriver driver, String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\" + fileName + ".png";
        FileUtils.copyFile(srcFile, new File(filePath));
        System.out.println("📸 Screenshot saved at: " + filePath);
    }
}
