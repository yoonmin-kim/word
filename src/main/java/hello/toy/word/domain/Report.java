package hello.toy.word.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Report {

    @Id @GeneratedValue
    private Long reportId;

    private String targetWord;
    private String replaceWord;
}
