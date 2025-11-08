package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class TC027 {

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
            System.out.println("Opened https://askomdch.com/ successfully.");

            // Step 3: Scroll to banner section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 600);");
            Thread.sleep(2000);

            // Step 4: Expected text titles
            List<String> expectedTexts = Arrays.asList(
                    "20% Off on Tank Tops",
                    "Latest Eyewear",
                    "Let’s Suit Up"
            );

            boolean allTextsFound = true;

            for (String text : expectedTexts) {
                List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
                if (elements.isEmpty()) {
                    System.out.println("❌ Missing banner text: " + text);
                    allTextsFound = false;
                } else {
                    System.out.println("✅ Found banner text: " + text);
                }
            }

            // Step 5: Result & Screenshot
            if (allTextsFound) {
                System.out.println("✅ Test Passed: All promotional texts are displayed correctly.");
                captureScreenshot(driver, "TC027-Screenshot-pass.png");
            } else {
                System.out.println("❌ Test Failed: One or more promotional texts are missing.");
                captureScreenshot(driver, "TC027-Screenshot-fail.png");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred during test execution: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC027-Screenshot-fail.png");
            }
        } finally {
            // Step 6: Close browser
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

            System.out.println("📸 Screenshot saved successfully at: " + dest.getAbsolutePath());
        } catch (IOException ioe) {
            System.out.println("⚠️ Failed to save screenshot: " + ioe.getMessage());
        }
    }
}
