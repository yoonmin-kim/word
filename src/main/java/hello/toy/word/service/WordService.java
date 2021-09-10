package hello.toy.word.service;

import hello.toy.word.util.SqlUtils;
import hello.toy.word.util.WordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class WordService {

    private final WordUtils wordUtils;
    private final SqlUtils sqlUtils;

    public void makeReport(MultipartFile file) throws IOException, ClassNotFoundException {
        String[] targetWordArr = sqlUtils.getKeys().toArray(new String[sqlUtils.getKeys().size()]);
        String[] replaceWordArr = new String[targetWordArr.length];

        for (int i = 0; i < targetWordArr.length; i++) {
            String replaceWord = sqlUtils.getReplaceWord(targetWordArr[i]);
            replaceWordArr[i] = replaceWord;
        }

        wordUtils.makeReport(file, targetWordArr, replaceWordArr);
    }

    public boolean validateExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!"docx".equals(extension)) {
            return false;
        }
        return true;
    }
}
