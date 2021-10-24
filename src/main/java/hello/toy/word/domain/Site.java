package hello.toy.word.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Site extends BaseTime {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "site_name")
	private String name;

	@OneToMany(mappedBy = "site")
	private List<SiteSql> siteSqlList = new ArrayList<>();

	public Site() {
	}

	public Site(String name) {
		this.name = name;
	}

}
