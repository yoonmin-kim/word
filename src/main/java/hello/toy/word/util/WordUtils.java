package hello.toy.word.util;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WordUtils {

    //C:/intellij-workspace/test.docx
    public void makeReport(MultipartFile file, String[] targetWordArr, String[] replaceWordArr) throws IOException {
        XWPFDocument document = new XWPFDocument(file.getInputStream());

        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    replaceWord(targetWordArr, replaceWordArr, r);
                }
            }
        }

        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            replaceWord(targetWordArr, replaceWordArr, r);
                        }
                    }
                }
            }
        }


        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("(yyyy-MM-dd)"));
        String downLoadDir = System.getProperty("user.home") + "\\Downloads";
        FileOutputStream fos = new FileOutputStream(downLoadDir + "/" + localDateTime + file.getOriginalFilename());
        document.write(fos);

        fos.close();
        document.close();
    }

    private void replaceWord(String[] targetWordArr, String[] replaceWordArr, XWPFRun r) {
        String text = r.getText(0);
        for(int j = 0 ; j < targetWordArr.length ; j++){
            if (text != null && text.contains(targetWordArr[j])) {
                text = text.replace("{" + targetWordArr[j] + "}", replaceWordArr[j]);// your content
                r.setText(text, 0);
            }
        }
    }
}
