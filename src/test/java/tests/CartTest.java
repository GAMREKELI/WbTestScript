package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;
import utils.TestConfig;

import java.time.Duration;

public class CartTest {

    private static final TestConfig config = ConfigFactory.create(TestConfig.class);
    private WebDriver driver;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testAddProductToCard() {
        String productId = config.productId();
        String url = String.format(config.baseUrl(), productId);
        driver.get(url);
        productPage = new ProductPage(driver, productId);
        Assert.assertTrue(productPage.isPageOpened(), "Страница товара не открылась!");

        productPage.clickAddToCart();
        Assert.assertTrue(productPage.isAddToCartButtonClicked(), "Кнопка 'Добавить в корзину' не нажата!");

        productPage.clickGoToCart();
        cartPage = new CartPage(driver, productId);
        Assert.assertTrue(cartPage.isPageOpened(), "Страница корзины не открылась!");

        Assert.assertTrue(cartPage.isProductInCart(), "Товар отсуствует в корзине!");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
