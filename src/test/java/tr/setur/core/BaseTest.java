package tr.setur.core;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@BeforeAll
	public void globalSetUp() {
		driver = DriverFactory.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	@AfterAll
	public void globalTearDown() {
		DriverFactory.quitDriver();
	}

	protected WebElement waitVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	protected WebElement waitClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	protected void click(By locator) {
		waitClickable(locator).click();
	}

	protected void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	protected void safeClick(By locator) {
		int maxAttempts = 3;
		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				WebElement element = waitClickable(locator);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", element);
				element.click();
				return;
			} catch (ElementClickInterceptedException e) {
				dismissOverlays();
				try {
					WebElement element = waitClickable(locator);
					jsClick(element);
					return;
				} catch (Exception ignored) {}
			} catch (StaleElementReferenceException e) {
				// re-find next loop
			}
			sleep(250);
		}
		WebElement element = waitClickable(locator);
		jsClick(element);
	}

	protected void dismissOverlays() {
		try {
			((JavascriptExecutor) driver).executeScript(
				"var el=document.getElementById('ins-frameless-overlay'); if(el){el.style.display='none'; el.style.pointerEvents='none';}"
			);
		} catch (Exception ignored) {}
		By closeBtn = By.xpath("//button[contains(@aria-label,'close') or contains(@aria-label,'kapat') or normalize-space()='×' or contains(.,'Kapat')]");
		try {
			if (!driver.findElements(closeBtn).isEmpty()) {
				WebElement el = driver.findElement(closeBtn);
				jsClick(el);
			}
		} catch (Exception ignored) {}
	}

	protected void waitForHomeReady() {
		wait.until(d -> d.getTitle() != null && d.getTitle().toLowerCase().contains("setur"));
		wait.until(d -> String.valueOf(((JavascriptExecutor) d).executeScript("return document.readyState")).equals("complete"));
		dismissOverlays();
	}

	protected void sleep(long millis) {
		try { Thread.sleep(millis); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
	}
}
