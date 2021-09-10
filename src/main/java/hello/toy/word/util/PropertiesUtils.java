package hello.toy.word.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PropertiesUtils {

    private static Map<String, String> values = new ConcurrentHashMap<>();

    @PostConstruct
    public void load() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        FileReader fileReader = new FileReader(classPathResource.getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            String[] strings = StringUtils.delimitedListToStringArray(str, "=");
            if (strings.length > 2) {
                StringBuilder builder = new StringBuilder();
                int i = 0;
                for (String s : strings) {
                    if(i == 1) builder.append(s);
                    if(i !=0 && i != 1) builder.append("=" + s);
                    i++;
                }
                setValues(strings[0], builder.toString());
            }else{
                setValues(strings[0], strings[1]);
            }
        }

        log.info("PropertiesUtils Loaded!");

        bufferedReader.close();
        fileReader.close();
    }

    public String read() {
        StringBuilder builder = new StringBuilder();
        Set<String> keys = values.keySet();
        for (String key : keys) {
            builder.append(key).append("=").append(getValues(key)).append("\n");
        }

        return builder.toString();
    }

    public boolean write(String propContext) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        FileWriter fileWriter = new FileWriter(classPathResource.getFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(propContext);

        bufferedWriter.close();
        fileWriter.close();

        clearValues();
        load();

        return true;
    }

    public String getValues(String key) {
        return values.get(key);
    }

    public void setValues(String key, String value) {
        values.put(key, value);
    }

    public void clearValues() {
        values.clear();
    }

}
