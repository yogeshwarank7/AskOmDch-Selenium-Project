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

public class TC043 {

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

            // Step 3: Locate product price elements
            List<WebElement> priceElements = driver.findElements(By.cssSelector(".woocommerce ul.products li span.price"));
            if (priceElements.isEmpty()) {
                throw new Exception("❌ No product prices found on the homepage.");
            }

            boolean allPricesDisplayed = true;

            System.out.println("💰 Product Price Details:");
            for (WebElement price : priceElements) {
                String priceText = price.getText();
                System.out.println("➡️ " + priceText);

                // Check for strikethrough price (discounted products)
                List<WebElement> oldPrices = price.findElements(By.cssSelector("del"));
                List<WebElement> newPrices = price.findElements(By.cssSelector("ins"));

                if (!oldPrices.isEmpty() && !newPrices.isEmpty()) {
                    System.out.println("✅ Discounted Product: Old Price - " +
                            oldPrices.get(0).getText() + " | New Price - " + newPrices.get(0).getText());
                } else if (newPrices.isEmpty()) {
                    System.out.println("✅ Regular Product: Price - " + priceText);
                } else {
                    System.out.println("⚠️ Price format unexpected: " + priceText);
                    allPricesDisplayed = false;
                }
            }

            // Step 4: Validate all prices
            if (allPricesDisplayed) {
                System.out.println("✅ [PASS] All product prices are displayed correctly with strikethrough for old prices.");
                takeScreenshot(driver, "TC043_Pass");
            } else {
                throw new Exception("❌ [FAIL] Some product prices not displayed or formatted correctly.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test case failed: " + e.getMessage());

            // Capture screenshot on failure
            try {
                if (driver != null) {
                    takeScreenshot(driver, "TC043_Fail");
                }
            } catch (IOException ioEx) {
                System.out.println("❌ Failed to save screenshot: " + ioEx.getMessage());
            }

        } finally {
            // Step 5: Close browser
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
