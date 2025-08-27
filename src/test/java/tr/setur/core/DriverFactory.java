package tr.setur.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
	private static WebDriver driver;

	private DriverFactory() {}

	public static WebDriver getDriver() {
		if (driver == null) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			// options.addArguments("--headless=new");
			driver = new ChromeDriver(options);
		}
		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
