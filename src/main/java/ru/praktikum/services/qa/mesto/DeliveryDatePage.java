package ru.praktikum.services.qa.mesto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;


public class DeliveryDatePage {
    private WebDriver driver;
    private WebDriverWait wait;


    // Локатор для заголовка
    private By headerLocatorRent = By.xpath("//div[@class='Order_Header__BZXOb' and text()='Про аренду']");

    // Локатор выбора даты
    private By dateInput = By
            .xpath("//*[@class='react-datepicker-wrapper']");

    // Локатор срока аренды
    private By rentInput = By
            .xpath("//*[@class='Dropdown-control']");

    // Локатор выпадающего списка аренды
    private By rentalDropDown = By
            .xpath("//*[@class='Dropdown-menu']");

    // Локатор для выбора первого элемента в списке срока аренды
    private By firstRentalOption = By.xpath("(//div[@class='Dropdown-menu']//div)[1]");

    // Локатор выбора цвета
    private By colorScooter = By
            .xpath("//*[@id='black']");

    // Локатор для комментария
    private By commentInput = By
            .xpath("//*[@placeholder='Комментарий для курьера']");

    // Локатор для кнопки заказать
    private By finalOrderButton = By
            .xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");


    //Кнопки Да/Нет в подтверждении заказа
    private By buttonYes = By.xpath("//button[text()= 'Да']");
    private By buttonNo = By.xpath("//button[text()= 'Нет']");


    //Сообщение об успешном заказе

    private By orderModal = By.xpath("//div[text()= 'Заказ оформлен']");

    public DeliveryDatePage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // метод для получения текста элемента в заголовке
    public String textInHeader() {
        WebElement headerElement = driver.findElement(headerLocatorRent);
        return headerElement.getText();
    }

    // Метод для установки даты не наступившего дня
    public void setFutureDate(int daysAhead) {
        // Открываем календарь
        driver.findElement(dateInput).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker")));

        // Получаем текущую дату и прибавляем daysAhead
        LocalDate targetDate = LocalDate.now().plusDays(daysAhead);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        String dayString = targetDate.format(formatter);

        // Находим все дни в календаре и выбираем нужный
        List<WebElement> days = driver.findElements(By.xpath("//div[contains(@class, 'react-datepicker__day')]"));
        for (WebElement day : days) {
            String dayText = day.getText();
            if (dayText.equals(dayString) && day.isDisplayed()) {
                day.click();
                break;
            }
        }
    }

    // Метод для выбора срока аренды: выбираем первый элемент из списка
    public void selectRentTerm() {
        // Открываем выпадающий список
        driver.findElement(rentInput).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentalDropDown));

        // Выбираем первый элемент списка
        WebElement firstOption = driver.findElement(firstRentalOption);
        firstOption.click();
    }

    // Метод для выбора цвета (например, черный)
    public void selectColor() {
        driver.findElement(colorScooter).click();
    }

    // Метод для ввода комментария
    public void enterComment(String comment) {
        WebElement commentField = driver.findElement(commentInput);
        commentField.clear();
        commentField.sendKeys(comment);
    }

    // Метод для подтверждения заказа (нажать "Заказать")
    public void clickOrderButton() {

        driver.findElement(finalOrderButton).click();
    }

    // Метод для подтверждения заказа - нажать "Да"
    public void confirmOrder() {

        wait.until(ExpectedConditions.elementToBeClickable(buttonYes));
        driver.findElement(buttonYes).click();
    }

    // Метод для отмены или отказа
    public void declineOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonNo));
        driver.findElement(buttonNo).click();
    }

    // Метод для проверки, что заказ успешно оформлен
    public boolean isOrderSuccess() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderModal)).isDisplayed();
    }

    // Проверка отображения кнопки заказать

    public boolean isOrderButtonAccessible() {
        try {
            WebElement button = driver.findElement(finalOrderButton);
            return button.isDisplayed() && button.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
