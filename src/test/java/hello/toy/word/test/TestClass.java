package hello.toy.word.test;

import hello.toy.word.ReloadConfig;
import hello.toy.word.util.PropertiesUtils;
import hello.toy.word.util.SqlUtils;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Locale;

@SpringBootTest
public class TestClass {

//    @Value("#{dbsetting['db.user']}")
//    private String test;

    @Test
    void dbTest() throws ClassNotFoundException {
        ClassPathResource resource = new ClassPathResource("properties/dbsetting.properties");


        Class.forName(PropertiesUtils.getValues("db.classforname"));

        String url=PropertiesUtils.getValues("db.url");
        String user=PropertiesUtils.getValues("db.user");
        String password=PropertiesUtils.getValues("db.password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
//        String QUERY = "select replace_word from report";
        String QUERY = SqlUtils.getValues("test");
        String result = "";
        try {
            conn = DriverManager.getConnection(url, user, password);
            pstmt = conn.prepareStatement(QUERY);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                result = rs.getString("test");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        System.out.println(result);


    }

//    @Autowired
//    ReloadConfig reloadConfig;

//    @Test
//    void configurationTest() {
//        String string = reloadConfig.getCompositeConfiguration().getString("db.url");
//        System.out.println("string = " + string);
//    }
}
