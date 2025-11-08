package TS004;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC039 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // 🔹 Set ChromeDriver path
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // 🔹 Launch Chrome browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // 🔹 Step 1: Open homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Step 1: Homepage opened successfully.");

            // 🔹 Step 2: Scroll to “Featured Products” section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 1200)");
            Thread.sleep(2000);
            System.out.println("✅ Step 2: Scrolled down to 'Featured Products' section.");

            // 🔹 Step 3: Verify “Featured Products” heading visibility
            WebElement featuredHeading = driver.findElement(By.xpath("//h2[contains(text(),'Featured Products')]"));

            if (featuredHeading.isDisplayed()) {
                String headingText = featuredHeading.getText();
                String headingColor = featuredHeading.getCssValue("color");
                String textAlign = featuredHeading.getCssValue("text-align");

                System.out.println("✅ Step 3: 'Featured Products' heading is visible.");
                System.out.println("📖 Heading Text: " + headingText);
                System.out.println("🎨 Heading Color: " + headingColor);
                System.out.println("📐 Text Alignment: " + textAlign);

                // 🔹 Validation for readability & alignment
                if (headingText.equalsIgnoreCase("Featured Products")) {
                    System.out.println("🎉 TS039 PASS: Heading is clearly visible and properly aligned.");
                    takeScreenshot(driver, "TC039-Screenshot-pass");
                } else {
                    System.out.println("❌ TS039 FAIL: Heading text mismatch or not properly displayed.");
                    takeScreenshot(driver, "TC039-Screenshot-fail");
                }
            } else {
                System.out.println("❌ TS039 FAIL: 'Featured Products' heading not visible.");
                takeScreenshot(driver, "TC039-Screenshot-fail");
            }

        } catch (Exception e) {
            System.out.println("❌ TS039 FAIL: Exception occurred - " + e.getMessage());
            try {
                if (driver != null) {
                    takeScreenshot(driver, "TC039-Screenshot-fail");
                }
            } catch (IOException ex) {
                System.out.println("⚠️ Unable to capture screenshot: " + ex.getMessage());
            }

        } finally {
            // 🔹 Close the browser safely
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility
    public static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                + fileName + ".png";
        FileHandler.copy(srcFile, new File(filePath));
        System.out.println("📸 Screenshot saved at: " + filePath);
    }
}
