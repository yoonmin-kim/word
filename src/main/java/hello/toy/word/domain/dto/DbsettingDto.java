package hello.toy.word.domain.dto;

import hello.toy.word.domain.Site;
import lombok.Data;

@Data
public class DbsettingDto {

	private Long id;
	private String classForName;
	private String url;
	private String user;
	private String password;
	private SiteDto siteDto;

	public void putSiteDto(SiteDto siteDto) {
		this.siteDto = siteDto;
	}
}
