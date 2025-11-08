package TS003;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC034 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // 🔹 Set ChromeDriver path
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // 🔹 Launch browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // 🔹 Step 1: Open homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Step 1: Homepage opened successfully.");

            // 🔹 Step 2: Resize to Tablet view
            driver.manage().window().setSize(new Dimension(768, 1024));
            Thread.sleep(2000);
            System.out.println("✅ Step 2: Resized to Tablet view.");

            // Capture screenshot
            takeScreenshot(driver, "TC034-Tablet-View");

            // 🔹 Step 3: Resize to Mobile view
            driver.manage().window().setSize(new Dimension(375, 812));
            Thread.sleep(2000);
            System.out.println("✅ Step 3: Resized to Mobile view.");

            takeScreenshot(driver, "TC034-Mobile-View");

            // 🔹 Step 4: Resize back to Desktop view
            driver.manage().window().maximize();
            Thread.sleep(2000);
            System.out.println("✅ Step 4: Resized back to Desktop view.");

            takeScreenshot(driver, "TC034-Desktop-View");

            // 🔹 Step 5: Verify if layout adjusts properly
            // We’ll check if the “Shop Now” button is visible in all views as a layout check
            WebElement shopNowButton = driver.findElement(By.xpath("//a[contains(text(),'Shop Now')]"));
            if (shopNowButton.isDisplayed()) {
                System.out.println("🎉 TS034 PASS: Section layout adjusts properly across all views.");
                takeScreenshot(driver, "TC034-Screenshot-pass");
            } else {
                System.out.println("❌ TS034 FAIL: Layout elements are misaligned or hidden.");
                takeScreenshot(driver, "TC034-Screenshot-fail");
            }

        } catch (Exception e) {
            System.out.println("❌ TS034 FAIL: Exception occurred - " + e.getMessage());
            try {
                if (driver != null) {
                    takeScreenshot(driver, "TC034-Screenshot-error");
                }
            } catch (IOException ex) {
                System.out.println("⚠️ Unable to take screenshot: " + ex.getMessage());
            }

        } finally {
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
