package hello.toy.word.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class SqlUtils {

    private static Map<String, String> values = new HashMap<>();

    private SqlUtils() {}

    @PostConstruct
    public static void load() throws ParserConfigurationException, IOException, SAXException {
        ClassPathResource resource = new ClassPathResource("sql/sql.xml");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(resource.getFile());
        document.getDocumentElement().normalize();
        NodeList personTagList = document.getElementsByTagName("sql");

        for (int i = 0; i < personTagList.getLength(); ++i) {
            NodeList childNodes = personTagList.item(i).getChildNodes();
            for (int j = 0; j < childNodes.getLength(); ++j) {
                if(!"#text".equals(childNodes.item(j).getNodeName())){
                    setValues(childNodes.item(j).getNodeName(), childNodes.item(j).getTextContent());
                }
            }
        }

    }

    public static String read() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("sql/sql.xml");
        FileReader fileReader = new FileReader(classPathResource.getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String str = "";
        StringBuilder builder = new StringBuilder();
        while ((str = bufferedReader.readLine()) != null) {
            builder.append(str + "\n");
        }

        bufferedReader.close();
        fileReader.close();

        return builder.toString();
    }

    public static void write(String xmlContext) throws IOException, ParserConfigurationException, SAXException {
        ClassPathResource classPathResource = new ClassPathResource("sql/sql.xml");
        FileWriter fileWriter = new FileWriter(classPathResource.getFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(xmlContext);

        bufferedWriter.close();
        fileWriter.close();

        clearValues();
        load();
    }

    public static String getValues(String key) {
        return values.get(key);
    }

    public static void setValues(String key, String value) {
        values.put(key, value);
    }

    public static void clearValues() {
        values.clear();
    }

    public static Set<String> getKeys() {
        return values.keySet();
    }

    public static String getReplaceWord(String targetWord) throws ClassNotFoundException {

        Class.forName(PropertiesUtils.getValues("db.classforname"));

        String url = PropertiesUtils.getValues("db.url");
        String user = PropertiesUtils.getValues("db.user");
        String password = PropertiesUtils.getValues("db.password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String QUERY = getValues(targetWord);
        String replaceWord = "";
        try {
            conn = DriverManager.getConnection(url, user, password);
            pstmt = conn.prepareStatement(QUERY);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                replaceWord = rs.getString(targetWord);
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

        return replaceWord;
    }
}
