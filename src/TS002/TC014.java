package TS002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.Color;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC014 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            // Step 2: Open the website
            driver.get("https://askomdch.com/");
            System.out.println("✅ Opened https://askomdch.com/ successfully.");

            // Step 3: Locate the main banner heading (h1)
            WebElement heading = driver.findElement(
                    By.xpath("//h1[@class='alignwide has-text-align-center has-white-color has-text-color has-huge-font-size']")
            );

            // Step 4: Get heading properties
            boolean isDisplayed = heading.isDisplayed();
            String headingText = heading.getText().trim();
            String fontWeight = heading.getCssValue("font-weight");
            String textAlign = heading.getCssValue("text-align");
            String color = Color.fromString(heading.getCssValue("color")).asHex();

            System.out.println("Heading Text: " + headingText);
            System.out.println("Font Weight: " + fontWeight);
            System.out.println("Text Align: " + textAlign);
            System.out.println("Text Color: " + color);

            // Step 5: Validate visual properties
            boolean isBold = fontWeight.equals("700") || fontWeight.equals("bold");
            boolean isCentered = textAlign.equalsIgnoreCase("center");
            boolean isTextCorrect = headingText.equalsIgnoreCase("Raining Offers for Hot Summer!");

            // Step 6: Verify and take screenshot of only the heading element
            if (isDisplayed && isTextCorrect && isBold && isCentered) {
                System.out.println("✅ Test Passed: Heading is bold, centered, and clearly visible.");
                takeElementScreenshot(heading, "TC014-Screenshot-Pass");
            } else {
                System.out.println("❌ Test Failed: Heading style or alignment mismatch.");
                takeElementScreenshot(heading, "TC014-Screenshot-Fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());
            if (driver != null) {
                takeFullScreenshot(driver, "TC014-Screenshot-Fail");
            }
        } finally {
            // Step 7: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed after execution.");
            }
        }
    }

    // ✅ Screenshot of only the specific element
    public static void takeElementScreenshot(WebElement element, String fileName) {
        try {
            File srcFile = element.getScreenshotAs(OutputType.FILE);
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            new File(folderPath).mkdirs();
            File destFile = new File(folderPath + fileName + ".png");
            FileHandler.copy(srcFile, destFile);
            System.out.println("📸 Element screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save element screenshot: " + e.getMessage());
        }
    }

    // Backup full-page screenshot in case of failure
    public static void takeFullScreenshot(WebDriver driver, String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            new File(folderPath).mkdirs();
            File destFile = new File(folderPath + fileName + ".png");
            FileHandler.copy(srcFile, destFile);
            System.out.println("📸 Full-page screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save full screenshot: " + e.getMessage());
        }
    }
}
