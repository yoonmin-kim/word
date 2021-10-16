package hello.toy.word.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Site {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "site_name")
	private String name;

	public Site() {}

	public Site(String name) {
		this.name = name;
	}


}
