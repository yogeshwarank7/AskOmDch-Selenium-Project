package TS001;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;

public class TC009 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Navigate to website
            driver.get("https://askomdch.com");

            // Step 3: Click "About" link
            driver.findElement(By.xpath("//a[normalize-space()='About']")).click();

            // Step 4: Verify About page
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.toLowerCase().contains("about")) {
                System.out.println("✅ Test Passed: 'About Us' page opened successfully.");
                captureScreenshot(driver, "TC009-Screenshot-pass");
            } else {
                System.out.println("❌ Test Failed: Incorrect page opened. URL - " + currentUrl);
                captureScreenshot(driver, "TC009-Screenshot-fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception: " + e.getMessage());
            if (driver != null) {
                captureScreenshot(driver, "TC009-Screenshot-fail");
            }
        } finally {
            try {
                if (driver != null) {
                    Thread.sleep(1500);
                    driver.quit();
                    System.out.println("Browser closed successfully.");
                }
            } catch (Exception closeErr) {
                System.out.println("⚠️ Error closing browser: " + closeErr.getMessage());
            }
        }
    }

    // ✅ Fully reliable screenshot method — saves in project folder
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            // Folder inside your project
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";

            File destDir = new File(folderPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
                System.out.println("📁 Created folder: " + destDir.getAbsolutePath());
            }

            // Wait briefly to ensure full render
            Thread.sleep(1000);

            // Capture screenshot to temporary file
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            if (!srcFile.exists()) {
                System.out.println("⚠️ Screenshot source file not created!");
                return;
            }

            // Destination file
            File destFile = new File(destDir, fileName + ".png");

            // Manual byte copy (reliable)
            try (FileInputStream fis = new FileInputStream(srcFile);
                 FileOutputStream fos = new FileOutputStream(destFile)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            // Confirm saved file
            if (destFile.exists() && destFile.length() > 0) {
                System.out.println("📸 Screenshot saved successfully at: " + destFile.getAbsolutePath());
            } else {
                System.out.println("⚠️ Screenshot file missing after save attempt.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Screenshot save failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
