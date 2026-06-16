# Setur Web UI Data-Driven Automation Project

An advanced, data-driven web test automation framework designed to validate critical user journeys, comprehensive search filters, and booking flows on the Setur e-commerce platform.

---

### Key Architectural Highlights

*   **Data-Driven Testing (DDT):** Integrated external data sourcing via custom `CsvReader` utilities to pull dynamic test parameters (e.g., `destinations.csv`) for decoupled data and test script logic.
*   **Decoupled Page Object Model (POM):** Achieved industry-standard modularity by strictly separating component locators (`locators/HomePageLocators`) from behavioral page actions (`pages/HomePage`, `pages/ResultsPage`).
*   **Robust Synchronization:** Leveraged explicit wait mechanisms (`WebDriverWait`) over brittle hardcoded sleeps to guarantee resilient element interactions under dynamic loading conditions.
*   **Complex UI Automations:** Successfully automated multi-step calendar date pickers, conditional dropdown increments (adult count adjustments), and geographic keyword routing validation.

---

### Tech Stack

*   **Language:** Java
*   **Automation Tool:** Selenium WebDriver
*   **Testing Framework:** JUnit / TestNG
*   **Data Strategy:** CSV Data Ingestion (`CsvReader`)
*   **Build Tool:** Maven

---

### Project Architecture

*   `src/test/java/tr/setur/core`: Centralized initialization containing `BaseTest` configurations and dynamic `DriverFactory` logic.
*   `src/test/java/tr/setur/locators`: Distinct locator definition files keeping UI changes independent of tests.
*   `src/test/java/tr/setur/pages`: High-level business action methods masking raw driver interactions.
*   `src/test/java/tr/setur/tests`: Executable end-to-end verification suites (`SeturFlowTest`) ensuring correct transactional flows.
*   `src/test/resources/data`: External storage repository for dynamic environment configurations and test parameters (`destinations.csv`).
