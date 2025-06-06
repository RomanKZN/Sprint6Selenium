package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    //Кнопка открытия выпадающего текста
    private By dropDownButton = By.xpath("//div[@id= 'accordion__heading-0']");

    // Кнопка "Заказать" в начале страницы

    private By orderButtonInHeader = By
            .xpath("//button[@class='Button_Button__ra12g' and text()='Заказать']");


    // Кнопка "Заказать" в конце страницы

    private By orderButtonInFooter = By
            .xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }


    // метод ожидания загрузки страницы
    public void waitForLoadHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3)
        )
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("header__user")));
    }


    // метод для нажатия на кнопку заказа в начале страницы


    public void orderButtonInHeader() {
        driver.findElement(orderButtonInHeader).click();

    }

    // метод для нажатия на кнопку заказа в конце страницы

    public void orderButtonInFooter() {
        WebElement footerButton = driver.findElement(orderButtonInFooter);
        // Скроллим до элемента
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", footerButton);
        // Ждем, чтобы элемент был кликабельным
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(orderButtonInFooter));
        footerButton.click();
    }

    // метод для нажатия на кнопку выпадающего текста

    public void dropDownButton() {
        WebElement expandButton = driver.findElement(dropDownButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", expandButton);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(dropDownButton));
        expandButton.click();
    }

    // Метод для поиска текста после нажатия
    public WebElement getTextElement() {
        String expectedText = "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), '" + expectedText + "')]")));
    }


}


