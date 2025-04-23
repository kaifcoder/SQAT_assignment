package org.example.testingDemo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
public class SelemiumTestingClass {
    public static void main(String[] args) throws Exception {
        // Set the path to the ChromeDriver executable
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Navigate to the URL
        driver.manage().window().maximize();
        // Maximize the browser window
        driver.get("https://www.amazon.in");

        // TC01: Valid search
        WebElement searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        takeScreenshot(driver, "TC01_valid_search");

// TC02: Empty search
        driver.get("https://www.amazon.in");
        searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("");
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        takeScreenshot(driver, "TC02_empty_search");
// TC03: Special characters
        driver.get("https://www.amazon.in");
        searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("@!$#");
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        takeScreenshot(driver, "TC03_invalid_search");

        // TC04: Valid PIN
        driver.get("https://www.amazon.in");
        WebElement location = driver.findElement(By.id("nav-global-location-popover-link"));
        location.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement pinInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("GLUXZipUpdateInput")));
        pinInput.sendKeys("560001");
        driver.findElement(By.id("GLUXZipUpdate")).click();
        Thread.sleep(3000);
        takeScreenshot(driver, "TC04_valid_pin");

        // TC05: Invalid PIN (too short)
        driver.get("https://www.amazon.in");
        location = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-global-location-popover-link")));
        location.click();
        pinInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("GLUXZipUpdateInput")));
        pinInput.clear();
        pinInput.sendKeys("123");
        driver.findElement(By.id("GLUXZipUpdate")).click();
        Thread.sleep(3000);
        takeScreenshot(driver, "TC05_invalid_pin_short");

        // TC06: Non-numeric PIN
        driver.get("https://www.amazon.in");
        location = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-global-location-popover-link")));
        location.click();
        pinInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("GLUXZipUpdateInput")));
        pinInput.clear();
        pinInput.sendKeys("abcde");
        driver.findElement(By.id("GLUXZipUpdate")).click();
        Thread.sleep(3000);
        takeScreenshot(driver, "TC06_invalid_pin_non_numeric");
        // quit the browser
        driver.quit();
    }
    public static void takeScreenshot(WebDriver driver, String fileName) throws Exception {
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scr, new File("screenshots/" + fileName + ".png"));
    }
}
