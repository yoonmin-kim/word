package hello.toy.word.service;

import hello.toy.word.util.PropertiesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PropertiesService {

    private final PropertiesUtils propertiesUtils;

    public boolean save(String props) throws IOException {

        if(!validateProps(props)) return false;

        return propertiesUtils.write(props);
    }

    //검증로직(key=value, 형태로 잘 넘어왔는지 확인)
    public boolean validateProps(String props) {
        String[] delimitedProps = StringUtils.delimitedListToStringArray(props, "\r\n");

        for (String prop : delimitedProps) {
            String[] delimitedProp = StringUtils.delimitedListToStringArray(prop, "=");
            if (delimitedProp.length < 2) {
                return false;
            }
        }

        return true;
    }
}
