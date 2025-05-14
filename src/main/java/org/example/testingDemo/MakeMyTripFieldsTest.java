package org.example.testingDemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class MakeMyTripFieldsTest {
    public static void main() throws Exception {
        // Set path to chromedriver executable if needed
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Use explicit wait for synchronization (implicit waits not mixed with explicit here)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Define test data: each pair is {FromValue, ToValue}
        String[][] testData = {
//                {"Delhi", "Mumbai"},        // TC01: Valid and distinct
//                {"Chennai", "Chennai"},     // TC02: Valid but identical
//                {"P", "Bangalore"},       // TC03: From too short (invalid - too short)
//                {"Ahmedabad", "Thiruvananthapuram"}, // TC04: To too long (long name)
                {"12345", "Hyderabad"},     // TC05: From numeric
//                {"Kolkata", "67890"},       // TC06: To numeric
//                {"Ja@pur", "Surat"},        // TC07: From with special char
//                {"Pune", "Lu@know"},        // TC08: To with special char
        };
        Actions actions = new Actions(driver);

        for(int i = 0; i < testData.length; i++) {
            String fromInput = testData[i][0];
            String toInput = testData[i][1];
            try {
                // Navigate to MakeMyTrip flights page for each test
                driver.get("https://www.makemytrip.com/flights");

                // Close any pop-ups or initial overlays if present (optional)
                try {
                    WebElement popupClose = wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(".commonModal__close")));
                    popupClose.click();
                } catch (Exception ignored) {}

                if (fromInput.matches(".*\\d.*")) {
                    // Enter "To" field
                    WebElement toField1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("toCity")));
                    Thread.sleep(1000);
                    toField1.click();
                    Thread.sleep(1000);
                    WebElement to1 = driver.findElement(By.xpath("//input[@placeholder='To']"));
                    Thread.sleep(1000);
                    to1.clear();
                    Thread.sleep(1000);
                    to1.sendKeys(toInput);
                    Thread.sleep(2000);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//ul[@role='listbox']")));
                    Thread.sleep(2000);
                    WebElement firstSuggestion = driver.findElement(By.xpath("//ul[@role='listbox']/li[1]"));
                    firstSuggestion.click();
                    WebElement fromField1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromCity")));
                    fromField1.click();
                    Thread.sleep(1000);
                    WebElement from1 = driver.findElement(By.xpath("//input[@placeholder='From']"));
                    from1.clear();
                    Thread.sleep(1000);
                    from1.sendKeys(fromInput);
                    // take screenshot
                    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                    File targetFile = new File("screenshots/mmt/TC" + (4+1) + ".png");
                    FileUtils.copyFile(screenshot, targetFile);
                    continue;
                }

                // Enter "From" field
                WebElement fromField = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromCity")));
                fromField.click();
                Thread.sleep(1000);
                WebElement from = driver.findElement(By.xpath("//input[@placeholder='From']"));
                from.clear();
                Thread.sleep(1000);
                from.sendKeys(fromInput);



                // Wait briefly for autosuggestions to appear (if input is valid city)
                try {
                    Thread.sleep(2000);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//ul[@role='listbox']")));
                    Thread.sleep(2000);
                    WebElement firstSuggestion = driver.findElement(By.xpath("//ul[@role='listbox']/li[1]"));
                    firstSuggestion.click();
                } catch (Exception ignored) {}

                // Enter "To" field
                WebElement toField = wait.until(ExpectedConditions.elementToBeClickable(By.id("toCity")));
                toField.click();
                WebElement to = driver.findElement(By.xpath("//input[@placeholder='To']"));
                to.clear();
                to.sendKeys(toInput);
                // Select autosuggestion if available
                try {
                    Thread.sleep(1000);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//ul[@role='listbox']")));
                    Thread.sleep(1000);
                    WebElement firstSuggestion = driver.findElement(By.xpath("//ul[@role='listbox']/li[1]"));
                    firstSuggestion.click();
                    // select date if opened
                    // press TAB twice

                    actions.sendKeys("\uE004").perform();
                    Thread.sleep(2000);
                    actions.sendKeys("\uE004").perform();
                    Thread.sleep(2000);
                    actions.sendKeys("\uE004").perform();
                } catch (Exception ignored) {}

                // Validate that 'From' and 'To' are not identical
                if (!fromInput.isEmpty() && fromInput.equals(toInput)) {
                    System.out.println("Test " + (i+1) + " ERROR: 'From' and 'To' inputs are identical.");
                    // Optionally, record a test failure here or throw an exception
                }

            } catch (Exception e) {
                // Catch any unexpected errors (e.g. element not found, timeout)
                System.out.println("Test " + (4+1) + " encountered an exception: " + e.getMessage());
            } finally {
                // Take a screenshot for this test case
                try {
                    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                    File targetFile = new File("screenshots/mmt/TC" + (4+1) + ".png");
                    FileUtils.copyFile(screenshot, targetFile);
                } catch (Exception e) {
                    System.out.println("Could not capture screenshot for test " + (i+1) + ": " + e.getMessage());
                }
            }
        }

        driver.quit();
    }
}
