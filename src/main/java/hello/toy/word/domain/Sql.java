package hello.toy.word.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;

@Entity
@Getter
public class Sql {

	@Id
	@GeneratedValue
	private Long id;

	private String targetName;
	private String query;

	@OneToOne(fetch = FetchType.LAZY)
	private Site site;
}
