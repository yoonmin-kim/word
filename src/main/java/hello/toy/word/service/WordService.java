package hello.toy.word.service;

import hello.toy.word.util.SqlUtils;
import hello.toy.word.util.WordUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Service
public class WordService {

    public void makeReport(InputStream inputStream, String fileName) throws IOException, ClassNotFoundException {
        String[] targetWordArr = SqlUtils.getKeys().toArray(new String[SqlUtils.getKeys().size()]);
        String[] replaceWordArr = new String[targetWordArr.length];

        for (int i = 0; i < targetWordArr.length; i++) {
            String replaceWord = SqlUtils.getReplaceWord(targetWordArr[i]);
            replaceWordArr[i] = replaceWord;
        }

        WordUtils.makeReport(inputStream, fileName, targetWordArr, replaceWordArr);
    }
}
