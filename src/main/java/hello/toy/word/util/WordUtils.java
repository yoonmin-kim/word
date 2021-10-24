package hello.toy.word.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SqlDto;

@Component
public class WordUtils {

	public static final String REPLACE_WORD_START = "{";
	public static final String REPLACE_WORD_END = "}";
	public static final String FILE_NAME_PREFIX_PATTERN = "(yyyy-MM-dd)";
	public static final String DOWN_LOAD_DIR = System.getProperty("user.home") + "\\Downloads";

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

		String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FILE_NAME_PREFIX_PATTERN));
		String downLoadDir = DOWN_LOAD_DIR;
		FileOutputStream fos = new FileOutputStream(
			downLoadDir + File.separator + localDateTime + file.getOriginalFilename());
		document.write(fos);

		fos.close();
		document.close();
	}

	private void replaceWord(String[] targetWordArr, String[] replaceWordArr, XWPFRun r) {
		String text = r.getText(0);
		for (int j = 0; j < targetWordArr.length; j++) {
			if (text != null && text.contains(targetWordArr[j])) {
				text = text.replace(REPLACE_WORD_START + targetWordArr[j] + REPLACE_WORD_END, replaceWordArr[j]);
				r.setText(text, 0);
			}
		}
	}

	public String getReplaceWord(SqlDto sqlDto, DbsettingDto dbsettingDto) throws
		ClassNotFoundException,
		SQLException {

		Class.forName(dbsettingDto.getClassForName());

		String url = dbsettingDto.getUrl();
		String user = dbsettingDto.getUser();
		String password = dbsettingDto.getPassword();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String QUERY = sqlDto.getQuery();
		String replaceWord = "";
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(QUERY);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				replaceWord = rs.getString(sqlDto.getTargetName());
			}

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return replaceWord;
	}
}
