package hello.toy.word.domain.dto;

import hello.toy.word.domain.Site;
import lombok.Data;

@Data
public class SqlDto {

	private String targetName;
	private String query;
	private Site site;
}
