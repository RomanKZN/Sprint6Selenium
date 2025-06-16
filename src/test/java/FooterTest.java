import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.praktikum.services.qa.mesto.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooterTest {
    private WebDriver driver;
    private HomePage homePage;
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

        // Инициализация драйвера с опциями
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage = new HomePage(driver);
    }

    @AfterEach
    public void tearDown() {

            driver.quit();

    }
    private WebDriver createDriver(String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {
            // Инициализация драйвера Chrome
            return new org.openqa.selenium.chrome.ChromeDriver();
        }
        throw new UnsupportedOperationException("Браузер не поддерживается");
    }

    @Test
    public void testTextElementContainsExpectedText() {
        homePage.dropDownButton();

        // Получение элемента с ожидаемым текстом
        WebElement textElement = homePage.getTextElement();

        // Проверка, что элемент отображается и содержит нужный текст
        assertTrue(textElement.isDisplayed(), "Текст не отображается");
        String actualText = textElement.getText();
        String expectedText = "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";
        assertTrue(actualText.contains(expectedText), "Текст не совпадает");

    }


}
