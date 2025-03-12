package utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("file:src/test/resources/config.properties")
public interface TestConfig extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("product.id")
    String productId();
}
