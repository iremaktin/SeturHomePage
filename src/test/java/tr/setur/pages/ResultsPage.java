package tr.setur.pages;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {
	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By otherRegionsToggle = By.xpath("//button[contains(.,'Diğer Bölgeleri Göster') or contains(.,'Diğer Bölgeler') or contains(.,'Bölgeleri')] | //*[contains(.,'Diğer Bölgeleri Göster')]");
	private final By otherRegionOptions = By.xpath("//*[self::a or self::button][contains(.,'(') and contains(.,')')][not(contains(@aria-disabled,'true'))]");
	private final By sectionHeader = By.xpath("//h2[contains(.,'Antalya Otelleri') and contains(.,'Antalya Otel Fiyatları')]");

	public ResultsPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void assertUrlContainsAntalya() {
		wait.until(d -> driver.getCurrentUrl().toLowerCase().contains("antalya"));
	}

	public int clickRandomOtherRegionAndCaptureCount() {
		// bölge aç/kapa tıkla
		if (!driver.findElements(otherRegionsToggle).isEmpty()) {
			wait.until(ExpectedConditions.elementToBeClickable(otherRegionsToggle)).click();
		}
		List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(otherRegionOptions));
		if (items.isEmpty()) throw new AssertionError("Diğer Bölgeler listesi boş");
		int idx = new Random().nextInt(items.size());
		WebElement chosen = items.get(idx);
		String text = chosen.getText();
		int count = extractNumberInParentheses(text);
		chosen.click();
		return count;
	}

	private int extractNumberInParentheses(String s) {
		Matcher m = Pattern.compile("\\((\\d+)\\)").matcher(s);
		if (m.find()) return Integer.parseInt(m.group(1));
		throw new IllegalStateException("Parantez içinde sayı bulunamadı: " + s);
	}

	public void scrollToAntalyaHotelsHeaderAndAssertCountEquals(int expected) {
		WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(sectionHeader));
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", header);
		// Başlık yakınında parantez içinde toplam sayı metni olabilir, onu bul
		By nearCount = By.xpath("//h2[contains(.,'Antalya Otelleri')]/following::span[contains(.,'(') and contains(.,')')][1]");
		String txt = driver.findElements(nearCount).isEmpty() ? header.getText() : driver.findElement(nearCount).getText();
		int actual = extractNumberInParentheses(txt);
		if (actual != expected) {
			throw new AssertionError("Beklenen sayi=" + expected + ", bulunan=" + actual);
		}
	}
}
