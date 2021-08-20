package hello.toy.word.test;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class NormalTest {

    @Test
    void userHome() {
        String property = System.getProperty("user.home");
        System.out.println("property = " + property);
    }

    @Test
    void localDateTime() {
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("(yyyyMMdd)"));
        System.out.println(localDateTime);
    }

    @Test
    void originalFileName() {
        String fileName = "[0].dbforgemysql90.exe";
        System.out.println(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    @Test
    void test() {
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        System.out.println("classPathResource = " + classPathResource.getPath());
        System.out.println("classPathResource = " + classPathResource.getFilename());
        System.out.println("classPathResource = " + classPathResource.getDescription());

    }

    @Test
    void readProperties() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(classPathResource.getFile()));

        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println("str = " + str);
        }
        bufferedReader.close();

    }

    @Test
    void writeProperties() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("properties/dbsetting.properties");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(classPathResource.getFile()));

        String test = "db.classforname=com.mysql.cj.jdbc.Driver\n" +
                "db.url=jdbc:mysql://localhost/shopdb?serverTimezone=UTC\n" +
                "db.user=root\n" +
                "db.password=1234";

        bufferedWriter.write(test);
        bufferedWriter.close();
    }

    @Test
    void xmlTest() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Map<String, String> values = new HashMap<>();

        ClassPathResource resource = new ClassPathResource("sql/sql.xml");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(resource.getFile());
        document.getDocumentElement().normalize();
        // XML 데이터 중 <person> 태그의 내용을 가져온다.
        NodeList personTagList = document.getElementsByTagName("sql");

        for (int i = 0; i < personTagList.getLength(); ++i) {
            NodeList childNodes = personTagList.item(i).getChildNodes();
            for (int j = 0; j < childNodes.getLength(); ++j) {
                if(!"#text".equals(childNodes.item(j).getNodeName())){
                    values.put(childNodes.item(j).getNodeName(), childNodes.item(j).getTextContent());
                }
            }
        }
        System.out.println("values['test'] = "+ values.get("test"));
        System.out.println("values['test2'] = "+ values.get("test2"));

//        XPathFactory xpathFactory = XPathFactory.newInstance();
//        XPath xpath = xpathFactory.newXPath();
//
//
//        XPathExpression expr = xpath.compile("//sql/test");
//        NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            NodeList child = nodeList.item(i).getChildNodes();
//            for (int j = 0; j < child.getLength(); j++) {
//                Node node = child.item(j);
//                System.out.println("현재 노드 이름 : " + node.getNodeName());
//                System.out.println("현재 노드 타입 : " + node.getNodeType());
//                System.out.println("현재 노드 값 : " + node.getTextContent());
//                System.out.println("현재 노드 네임스페이스 : " + node.getPrefix());
//                System.out.println("현재 노드의 다음 노드 : " + node.getNextSibling());
//                System.out.println("");
//            }
//        }

    }
}
