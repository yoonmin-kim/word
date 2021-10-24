package hello.toy.word.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import hello.toy.word.domain.dto.DbsettingDto;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dbsetting extends BaseTime {

	@Id
	@GeneratedValue
	private Long id;

	private String classForName;
	private String url;
	private String user;
	private String password;

	@OneToOne(fetch = FetchType.LAZY)
	private Site site;

	public void putSite(Site site) {
		this.site = site;
	}

	public void updateDbSetting(DbsettingDto dbsettingDto) {
		this.classForName = dbsettingDto.getClassForName();
		this.url = dbsettingDto.getUrl();
		this.user = dbsettingDto.getUser();
		this.password = dbsettingDto.getPassword();
	}

}
