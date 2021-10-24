package hello.toy.word.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hello.toy.word.controller.exception.WordSaveExtensionException;
import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SqlDto;
import hello.toy.word.util.WordUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WordService {

	public static final String WORD_EXTENSION = "docx";
	public static final String EXTENSION_DELIMITER = ".";
	private final WordUtils wordUtils;
	private final SiteSqlService siteSqlService;
	private final DbsettingService dbsettingService;
	private final MessageSource messageSource;

	public void makeReport(MultipartFile file, Long siteId) throws IOException, ClassNotFoundException, SQLException {
		List<SqlDto> sqlDtoList = siteSqlService.findSqlBySiteId(siteId);
		String[] targetWordArr = new String[sqlDtoList.size()];
		String[] replaceWordArr = new String[sqlDtoList.size()];
		for (int i = 0; i < sqlDtoList.size(); i++) {
			SqlDto sqlDto = sqlDtoList.get(i);
			DbsettingDto dbsettingDto = dbsettingService.findBySiteId(siteId);
			targetWordArr[i] = sqlDto.getTargetName();
			replaceWordArr[i] = wordUtils.getReplaceWord(sqlDto, dbsettingDto);
		}
		wordUtils.makeReport(file, targetWordArr, replaceWordArr);
	}

	public void validateSave(MultipartFile file) throws WordSaveExtensionException {
		if (!validateExtension(file)) {
			throw new WordSaveExtensionException(messageSource.getMessage("error.word.save", null, null));
		}
	}

	private boolean validateExtension(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(EXTENSION_DELIMITER) + 1);

		if (!WORD_EXTENSION.equals(extension)) {
			return false;
		}
		return true;
	}
}
