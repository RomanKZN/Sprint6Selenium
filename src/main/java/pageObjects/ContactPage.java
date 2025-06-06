package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Локатор для заголовка
    private By headerLocator = By.xpath("//div[@class='Order_Header__BZXOb']");

    // Поле имя
    private By nameInputField = By
            .xpath("//*[@placeholder= '* Имя']");

    // Поле фамилия
    private By surnameInputField = By
            .xpath("//*[@placeholder= '* Фамилия']");

    // Поле адрес
    private By addressInputField = By
            .xpath("//*[@placeholder= '* Адрес: куда привезти заказ']");

    // Поле станция метро
    private By metroInputField = By
            .xpath("//*[@placeholder= '* Станция метро']");

    // Выпадающий список метро

    private By selectedMetroValue = By
            .xpath("//*[@class='select-search__select']");

    // Поле номер телефона
    private By telephoneInputField = By
            .xpath("//*[@placeholder= '* Телефон: на него позвонит курьер']");


    // Кнопка "Далее"

    private By nextButton = By
            .xpath("//*[@class= 'Button_Button__ra12g Button_Middle__1CSJM']");

    // конструктор класса

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // метод для получения текста элемента в заголовке
    public String headerLocator() {
        WebElement headerElement = driver.findElement(headerLocator);
        return headerElement.getText();
    }

    public void fillOrderForm(String name, String surname, String address, String metro, String phoneNumber) {
        // Ввод имени
        WebElement nameField = driver.findElement(nameInputField);
        nameField.clear();
        nameField.sendKeys(name);

        // Ввод фамилии
        WebElement surnameField = driver.findElement(surnameInputField);
        surnameField.clear();
        surnameField.sendKeys(surname);

        // Ввод адреса
        WebElement addressField = driver.findElement(addressInputField);
        addressField.clear();
        addressField.sendKeys(address);

        // Ввод станции метро и ожидание выбранного значения
        selectMetroStation(metro);

        // Ввод номера телефона
        WebElement phoneField = driver.findElement(telephoneInputField);
        phoneField.clear();
        phoneField.sendKeys(phoneNumber);
    }

    private void selectMetroStation(String stationName) {
        // Ввод названия станции
        WebElement metroField = driver.findElement(metroInputField);
        metroField.clear();
        metroField.sendKeys(stationName);

        wait.until(ExpectedConditions.textToBePresentInElementLocated(selectedMetroValue, stationName));

        WebElement selectedValue = wait.until(ExpectedConditions.elementToBeClickable(selectedMetroValue));
        selectedValue.click();
    }




    // Клик по кнопке "Далее"

    public void clickNext() {
        WebElement nextBtn = driver.findElement(nextButton);
        nextBtn.click();
    }
}
