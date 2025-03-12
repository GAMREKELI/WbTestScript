package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CartPage {

    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private String productId;

    @FindBy(xpath = "//h1[contains(text(), 'Корзина')]")
    private WebElement cartTitle;

    @FindBy(xpath = "//div[contains(@class, 'list-item__wrap')]")
    private WebElement addedProduct;

    public CartPage(WebDriver driver, String productId) {
        this.driver = driver;
        this.productId = productId;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        logger.info("CartPage создан");
    }

    public boolean isPageOpened() {
        logger.info("Проверяем, отображается ли корзина");
        try {
            boolean isOpen = wait.until(ExpectedConditions.visibilityOf(cartTitle)).isDisplayed();
            logger.info("Страница 'Корзина' открыта: {}", isOpen);
            return isOpen;
        } catch (Exception e) {
            logger.error("Ошибка при проверке открытия страницы 'Корзина'", e);
            return false;
        }
    }

    public boolean isProductInCart() {
        logger.info("Проверяем, что товар {} находится в корзине", productId);
        try {
            boolean inCart = wait.until(ExpectedConditions.visibilityOf(addedProduct)).isDisplayed();
            logger.info("Товар {} находится в корзине", productId);
            return inCart;
        } catch (Exception e) {
            logger.error("Ошибка при проверке наличия товара {} в корзине", productId, e);
            return false;
        }
    }
}
