package tr.setur.locators;

import org.openqa.selenium.By;

public final class HomePageLocators {
	private HomePageLocators() {}

	// Top navbar: Otel
	public static final By NAV_OTEL_BY_TEXT = By.xpath("//a[normalize-space()='Otel']");
	public static final By NAV_OTEL_BY_HREF = By.cssSelector("a[href='/yurt-ici-oteller']");

	// Top navbar: Kıbrıs
	public static final By NAV_KIBRIS_BY_TEXT = By.xpath("//a[normalize-space()='Kıbrıs']");
	public static final By NAV_KIBRIS_BY_HREF = By.cssSelector("a[href='/kibris-otelleri']");

	// Top navbar: Tur
	public static final By NAV_TUR_BY_TEXT = By.xpath("//a[normalize-space()='Tur']");
	public static final By NAV_TUR_BY_HREF = By.cssSelector("a[href='/temalara-gore-turlar']");
}
