package TS004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class TC044 {

    public static void main(String[] args) {
        // Set the ChromeDriver path
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        WebDriver driver = null;

        try {
            // Initialize Chrome browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 1: Open browser and navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Navigated to AskOmDch homepage.");

            // Step 2: Locate the first product section
            WebElement productSection = driver.findElement(By.xpath("//div[@class='wp-block-group__inner-container'][.//h2[@class='has-text-align-center']]"));

            // Step 3: Scroll smoothly to the section
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", productSection);

            // Step 4: Wait 3 seconds for visibility
            Thread.sleep(3000);

            // Step 5: Take a screenshot only for that section
            File src = productSection.getScreenshotAs(OutputType.FILE);
            File dest = new File("C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\TC044-Screenshot-Pass.png");
            FileUtils.copyFile(src, dest);

            System.out.println("✅ TS044 Passed - Product section visible and captured successfully.");
            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("❌ TS044 Failed due to exception: " + e.getMessage());
            takeFullPageScreenshot(driver, "TC044-Screenshot-Fail");
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed successfully.");
            }
        }
    }

    // Fallback Screenshot (Full Page on Failure)
    public static void takeFullPageScreenshot(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\" + fileName + ".png");
            FileUtils.copyFile(src, dest);
            System.out.println("📸 Failure Screenshot saved at: " + dest.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("⚠️ Failed to capture failure screenshot: " + e.getMessage());
        }
    }
}
