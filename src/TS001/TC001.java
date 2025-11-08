package TS001;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class TC001 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // Step 1: Set up the WebDriver
            System.setProperty("webdriver.chrome.driver", 
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate to the website
            driver.get("https://askomdch.com");
            System.out.println("Navigated to https://askomdch.com");

            // Step 3: Locate the logo element in the header
            WebElement logo = driver.findElement(By.xpath("//a[@class='custom-logo-link']//img"));

            // Step 4: Validate the logo visibility and alignment
            if (logo.isDisplayed()) {
                System.out.println("✅ Logo 'AskOmDch' is visible in the header.");
            } else {
                throw new Exception("❌ Logo 'AskOmDch' is NOT visible in the header.");
            }

            // Optional: Check alignment (left side of header)
            int logoX = logo.getLocation().getX();
            if (logoX < 200) {
                System.out.println("✅ Logo is properly aligned on the left side of the header.");
            } else {
                System.out.println("⚠️ Logo is visible but not properly aligned.");
            }

            System.out.println("Test Case Passed ✅");

        } catch (Exception e) {
            System.out.println("❌ Test Case Failed - " + e.getMessage());

            // Screenshot logic on failure
            try {
                if (driver != null) {
                    TakesScreenshot ts = (TakesScreenshot) driver;
                    File src = ts.getScreenshotAs(OutputType.FILE);

                    // ✅ Create folder if it doesn’t exist
                    String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                        System.out.println("📁 Created folder: " + folderPath);
                    }

                    // ✅ File name and full path
                    String destPath = folderPath + "\\TC001_Screenshot.png";
                    FileUtils.copyFile(src, new File(destPath));
                    System.out.println("📸 Screenshot saved at: " + destPath);
                }
            } catch (IOException ioException) {
                System.out.println("⚠️ Failed to capture screenshot: " + ioException.getMessage());
            }

        } finally {
            // Step 5: Close the browser
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }
}
