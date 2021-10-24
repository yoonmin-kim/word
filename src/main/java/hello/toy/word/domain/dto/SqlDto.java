package hello.toy.word.domain.dto;

import lombok.Data;

@Data
public class SqlDto {

	private Long sqlId;
	private String targetName;
	private String query;
}
