package tr.setur.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import tr.setur.core.BaseTest;
import tr.setur.pages.HomePage;
import tr.setur.pages.ResultsPage;
import tr.setur.utils.CsvReader;

public class SeturFlowTest extends BaseTest {

	@Test
	@DisplayName("Setur e2e: arama ve sonuç doğrulamaları")
	public void e2eSearchAndValidate() {
		WebDriver driver = this.driver;
		WebDriverWait wait = this.wait;

		HomePage home = new HomePage(driver, wait);
		home.open();

		assertTrue(driver.getTitle().contains("Setur’dan güvenle al, tatilde kal! | Setur"), "Başlık bekleneni içermiyor");
		assertTrue(driver.getCurrentUrl().startsWith("https://www.setur.com.tr"), "URL bekleneni içermiyor");

		home.assertHotelTabIsDefault();

		String city = tr.setur.utils.CsvReader.readFirstValue("data/destinations.csv", "city");
		home.typeDestinationAndSelectTop(city);

		home.openDatePickerAndPickFirstWeekOfAprilOneWeek();

		home.increaseAdultAndAssertChanged();

		home.assertSearchVisibleAndClick();

		ResultsPage results = new ResultsPage(driver, wait);
		results.assertUrlContainsAntalya();

		int pickedCount = results.clickRandomOtherRegionAndCaptureCount();
		results.scrollToAntalyaHotelsHeaderAndAssertCountEquals(pickedCount);
	}
}
