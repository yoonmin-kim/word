package hello.toy.word;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Component
@Slf4j
public class ReloadConfig {

    private ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder;

    @PostConstruct
        // 초기화가 끝난 후 자동 실행
    void init() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        builder = new ReloadingFileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(new Parameters().fileBased().setFile(classPathResource.getFile()));
        // 설정 파일 위치를 적어준다.

        PeriodicReloadingTrigger configReloadingTrigger = new PeriodicReloadingTrigger(
                builder.getReloadingController(), null, 1, TimeUnit.SECONDS);
        //정보를 파일에서 리로드할 시간 설정

        configReloadingTrigger.start();
    }

    public Configuration getCompositeConfiguration() {
        try {
            return builder.getConfiguration(); //정보 읽음
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getKeys() {
        Iterator<String> keys = getCompositeConfiguration().getKeys();
        List<String> keyList = new ArrayList<String>();
        while(keys.hasNext()) {
            keyList.add(keys.next());
        }
        return keyList;
    }

    public String getString(String key) {
        return getCompositeConfiguration().getString(key);
    }

    public int getInt(String key) {
        return getCompositeConfiguration().getInt(key);
    }

}
