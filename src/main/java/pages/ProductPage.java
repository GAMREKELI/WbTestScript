package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class ProductPage {

    private static final Logger logger = LoggerFactory.getLogger(ProductPage.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private String productId;

    @FindBy(xpath = "//a[contains(@href, '/lk/basket')]")
    private WebElement goToCartButton;

    public ProductPage(WebDriver driver, String productId) {
        this.driver = driver;
        this.productId = productId;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        logger.info("ProductPage создан");
    }

    public boolean isPageOpened() {
        logger.info("Проверяем, открыта ли страница товара {}", productId);
        try {
            boolean isOpen = wait.until(ExpectedConditions.and(
                    ExpectedConditions.urlContains(productId),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@class, 'product-page__title')]"))
            ));
            logger.info("Страница товара {} открыта: {}", productId, isOpen);
            return isOpen;

        } catch (Exception e) {
            logger.error("Ошибка при проверке открытия страницы товара {}", productId, e);
            return false;
        }
    }

    public void clickAddToCart() {
        logger.info("Пытаемся добавить товар {} в корзину", productId);
        try {
            logger.info("Ищем кнопку 'Добавить в корзину' на странице товара {}", productId);
            List<WebElement> buttons = driver.findElements(By.xpath("//button[@aria-label='Добавить в корзину']"));

            WebElement correctButton = null;
            for (WebElement button : buttons) {
                if (button.isDisplayed() && button.isEnabled()) {
                    correctButton = button;
                    break;
                }
            }

            if (correctButton != null) {
                try {
                    logger.info("Кликаем по кнопке 'Добавить в корзину' товара {}", productId);
                    wait.until(ExpectedConditions.elementToBeClickable(correctButton)).click();
                    logger.info("Товар {} успешно добавлен в корзину!", productId);
                } catch (Exception e) {
                    logger.error("Ошибка добавления товара {} в корзину", productId, e);
                }
            } else {
                logger.warn("Не удалось найти доступную кнопку 'Добавить в корзину' для товара {}", productId);
            }

        } catch (TimeoutException e) {
            logger.error("Кнопка 'Добавить в корзину' не найдена или недоступна для товара {}", productId, e);
        }
    }

    public boolean isAddToCartButtonClicked() {
        logger.info("Проверяем, была ли нажата кнопка 'Добавить в корзину'");
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            boolean isClicked = shortWait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//button[@aria-label='Добавить в корзину']")
            ));
            logger.info("Кнопка 'Добавить в корзину' исчезла: {}", isClicked);
            return isClicked;
        } catch (TimeoutException e) {
            logger.warn("Кнопка 'Добавить в корзину' все еще видима для товара {}", productId);
            return false;
        }
    }

    public void clickGoToCart() {
        logger.info("Пытаемся перейти в корзину");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(goToCartButton)).click();
            logger.info("Переход в корзину успешно выполнен");
        } catch (Exception e) {
            logger.error("Ошибка при переходе в корзину", e);
        }
    }

}
