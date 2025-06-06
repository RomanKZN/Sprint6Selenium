import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import pageObjects.ContactPage;
import pageObjects.DeliveryDatePage;
import pageObjects.HomePage;

import java.util.stream.Stream;

public class OrderTest {

    // Метод для получения списка браузеров
    static Stream<String> browsers() {
        return Stream.of("chrome", "firefox");
    }

    // Общий для всех тестов метод для создания драйвера с WebDriverManager
    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox","--headless", "--disable-dev-shm-usage");
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
    // Аренда через кнопку "Заказать" в начале страницы
    public void testOrderViaHeaderButton(String browser) {
        WebDriver driver = createDriver(browser);
        try {
            driver.get("https://qa-scooter.praktikum-services.ru/");

            // Весь сценарий теста
            HomePage homePage = new HomePage(driver);
            homePage.orderButtonInHeader(); // клик по кнопке заказа в хедере

            // Проверка и заполнение формы контактов
            ContactPage contactPage = new ContactPage(driver);
            String headerText = contactPage.headerLocator();
            Assertions.assertEquals("Для кого самокат", headerText);

            contactPage.fillOrderForm(
                    "Иван",
                    "Иванов",
                    "ул. Ленина, д. 10",
                    "Комсомольская",
                    "+79991234567");
            contactPage.clickNext();

            // Переход к странице с выбором даты
            DeliveryDatePage deliveryPage = new DeliveryDatePage(driver);
            Assertions.assertEquals("Про аренду", deliveryPage.textInHeader());

            deliveryPage.setFutureDate(2);
            deliveryPage.selectRentTerm();
            deliveryPage.selectColor();
            deliveryPage.enterComment("Для друга");
            deliveryPage.clickOrderButton();
            deliveryPage.confirmOrder();

            // Проверка успешного оформления заказа
            Assertions.assertTrue(deliveryPage.isOrderSuccess(), "Заказ не подтвержден");

        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("browsers")
    // Аренда через кнопку "Заказать" в конце страницы и без комментария
    public void testOrderViaFooterButton(String browser) {
        WebDriver driver = createDriver(browser);
        try {
            driver.get("https://qa-scooter.praktikum-services.ru/");
            HomePage homePage = new HomePage(driver);
            homePage.orderButtonInFooter(); // клик по кнопке заказа в подвале

            // Проверка и заполнение формы контактов
            ContactPage contactPage = new ContactPage(driver);
            String headerText = contactPage.headerLocator();
            Assertions.assertEquals("Для кого самокат", headerText);

            contactPage.fillOrderForm(
                    "Иван",
                    "Иванов",
                    "ул. Ленина, д. 10",
                    "Комсомольская",
                    "+79991234567");
            contactPage.clickNext();

            // Переход к странице с выбором даты
            DeliveryDatePage deliveryPage = new DeliveryDatePage(driver);
            Assertions.assertEquals("Про аренду", deliveryPage.textInHeader());

            deliveryPage.setFutureDate(5);
            deliveryPage.selectRentTerm();
            deliveryPage.selectColor();
            deliveryPage.enterComment("");
            deliveryPage.clickOrderButton();
            deliveryPage.confirmOrder();

            // Проверка успешного оформления заказа
            Assertions.assertTrue(deliveryPage.isOrderSuccess(), "Заказ не подтвержден");
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("browsers")
    // Проверка кнопки отказа от аренды
    public void testTheCancelRentButton(String browser) {
        WebDriver driver = createDriver(browser);
        try {
            driver.get("https://qa-scooter.praktikum-services.ru/");
            HomePage homePage = new HomePage(driver);
            homePage.orderButtonInFooter(); // клик по кнопке заказа в подвале

            // Проверка и заполнение формы контактов
            ContactPage contactPage = new ContactPage(driver);
            String headerText = contactPage.headerLocator();
            Assertions.assertEquals("Для кого самокат", headerText);

            contactPage.fillOrderForm(
                    "Иван",
                    "Иванов",
                    "ул. Ленина, д. 10",
                    "Комсомольская",
                    "+79991234567");
            contactPage.clickNext();

            // Переход к странице с выбором даты
            DeliveryDatePage deliveryPage = new DeliveryDatePage(driver);
            Assertions.assertEquals("Про аренду", deliveryPage.textInHeader());

            deliveryPage.setFutureDate(5);
            deliveryPage.selectRentTerm();
            deliveryPage.selectColor();
            deliveryPage.enterComment("Для друга");
            deliveryPage.clickOrderButton();
            deliveryPage.declineOrder();

            // Проверка отображения и нажатия кнопки "Заказать"
            deliveryPage.clickOrderButton();


        } finally {
            driver.quit();
        }
    }

}