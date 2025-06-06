import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageObjects.HomePage;

import java.util.stream.Stream;

public class FooterTest {
    static Stream<String> browsers() {
        return Stream.of("chrome", "firefox");
    }

    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox","--headless" ,"--disable-dev-shm-usage");
                return new ChromeDriver(chromeOptions);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                return new FirefoxDriver(firefoxOptions);
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }
    }

    @ParameterizedTest
    @MethodSource("browsers")
    public void testDropDownButtonDisplaysCorrectText(String browser) {
        WebDriver driver = createDriver(browser);
        try {
            driver.get("https://qa-scooter.praktikum-services.ru/");

            // Создаем экземпляр страницы
            HomePage homePage = new HomePage(driver);

            // Вызов метода, который нажимает на кнопку
            homePage.dropDownButton();

            // Проверяем наличие текста
            WebElement textElement = homePage.getTextElement();
            String actualText = textElement.getText();

            Assertions.assertTrue(actualText.contains("Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                    "Текст не отображается или не совпадает");

        } finally {
            driver.quit();
        }
    }

}
