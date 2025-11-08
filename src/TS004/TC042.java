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

public class TC042 {

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

            // Step 3: Locate Featured Products section (scrolling handled automatically)
            List<WebElement> productNames = driver.findElements(By.cssSelector(".woocommerce ul.products li h2"));

            if (productNames.isEmpty()) {
                throw new Exception("❌ No product names found in the featured section.");
            }

            boolean allNamesVisible = true;

            // Step 4: Validate product names visibility and print them
            System.out.println("🛍️ Featured Product Names:");
            for (WebElement name : productNames) {
                if (name.isDisplayed()) {
                    System.out.println("✅ " + name.getText());
                } else {
                    System.out.println("❌ Product name not visible.");
                    allNamesVisible = false;
                }
            }

            // Step 5: Pass/Fail condition
            if (allNamesVisible) {
                System.out.println("✅ [PASS] All product names are displayed clearly.");
                takeScreenshot(driver, "TC042_Pass");
            } else {
                throw new Exception("❌ [FAIL] Some product names are not visible or missing.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test case failed: " + e.getMessage());
            try {
                if (driver != null) {
                    takeScreenshot(driver, "TC042_Fail");
                }
            } catch (IOException ioEx) {
                System.out.println("❌ Failed to save screenshot: " + ioEx.getMessage());
            }

        } finally {
            // Step 6: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility
    public static void takeScreenshot(ChromeDriver driver, String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\" + fileName + ".png";
        FileUtils.copyFile(srcFile, new File(filePath));
        System.out.println("📸 Screenshot saved at: " + filePath);
    }
}
