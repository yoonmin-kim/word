package hello.toy.word.util.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:properties/message.properties", encoding = "UTF-8")
public class MessageUtils {

    private final Environment env;

    public String getProperty(String key) {
        return env.getProperty(key);
    }
}
