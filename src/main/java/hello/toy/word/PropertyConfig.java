package hello.toy.word;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

//@Configuration
public class PropertyConfig {

    @Bean(name = "dbsetting")
    public PropertiesFactoryBean propertiesFactoryBean() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        propertiesFactoryBean.setLocation(classPathResource);
        return propertiesFactoryBean;
    }
}
