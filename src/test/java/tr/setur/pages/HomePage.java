package tr.setur.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By hotelTabButton = By.xpath("//button[contains(.,'Otel')]");
	private final By destinationInput = By.xpath("//input[@role='textbox' or @aria-label='search-input' or contains(@placeholder,'Nereye')] | //label[contains(.,'Nereye')]/following::input[1]");
	private final By searchButton = By.xpath("//button[normalize-space()='Ara']");

	public HomePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void open() {
		driver.get("https://www.setur.com.tr/");
	}

	public void assertHotelTabIsDefault() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(hotelTabButton));
	}

	public void typeDestinationAndSelectTop(String city) {
		WebElement input = wait.until(ExpectedConditions.elementToBeClickable(destinationInput));
		input.click();
		input.clear();
		input.sendKeys(city);
		// Bekleme ve ilk öneriyi Enter ile seçme
		new Actions(driver).pause(java.time.Duration.ofMillis(800)).sendKeys(Keys.ARROW_DOWN, Keys.ENTER).perform();
	}

	public void openDatePickerAndPickFirstWeekOfAprilOneWeek() {
		// Tarih açma: alan metnine göre yaklaşıp ilk tarih açılır
		By dateOpen = By.xpath("//div[contains(.,'Giriş - Çıkış Tarihleri') or contains(.,'Ne Kadar Kalacaksınız')][@role='button' or @tabindex] | //*[contains(.,'Giriş - Çıkış')][1]");
		wait.until(ExpectedConditions.elementToBeClickable(dateOpen)).click();
		// Nisan ayına git (takvimde 'Nisan' başlığına ilerle)
		By nextBtn = By.xpath("//button[contains(@aria-label,'next') or contains(@class,'next') or contains(.,'>')]");
		for (int i = 0; i < 6; i++) {
			if (driver.getPageSource().contains("Nisan")) break;
			wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
		}
		// Nisan ilk haftadan başlangıç (1-7)
		By aprilFirst = By.xpath("//div[contains(@class,'day') or self::td]//button[.='1' or .='02' or .='01' or .='1']");
		wait.until(ExpectedConditions.elementToBeClickable(aprilFirst)).click();
		By aprilSeventh = By.xpath("//div[contains(@class,'day') or self::td]//button[.='7' or .='07']");
		wait.until(ExpectedConditions.elementToBeClickable(aprilSeventh)).click();
		// Uygula/Done varsa tıkla
		By apply = By.xpath("//button[contains(.,'Uygula') or contains(.,'Tamam') or contains(.,'Bitti') or contains(.,'Apply')]");
		if (!driver.findElements(apply).isEmpty()) {
			wait.until(ExpectedConditions.elementToBeClickable(apply)).click();
		}
	}

	public void increaseAdultAndAssertChanged() {
		By guestOpen = By.xpath("//div[contains(.,'Kaç Kişi') or contains(.,'Yetişkin')][@role='button' or @tabindex] | //*[contains(.,'Kaç Kişi')][1]");
		wait.until(ExpectedConditions.elementToBeClickable(guestOpen)).click();
		By adultCount = By.xpath("//div[contains(.,'Yetişkin')]/following::div[1]//*[contains(@class,'count') or self::input][1]");
		String before = driver.findElements(adultCount).isEmpty() ? "" : driver.findElement(adultCount).getText();
		By plus = By.xpath("//div[contains(.,'Yetişkin')]/following::button[contains(@aria-label,'art') or contains(.,'+')][1]");
		wait.until(ExpectedConditions.elementToBeClickable(plus)).click();
		String after = driver.findElements(adultCount).isEmpty() ? "" : driver.findElement(adultCount).getText();
		if (before.equals(after)) throw new AssertionError("Yetişkin sayısı artmadı");
		// paneli kapatmak için dışarı tıkla
		new Actions(driver).moveByOffset(5,5).click().perform();
	}

	public void assertSearchVisibleAndClick() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
	}
}
