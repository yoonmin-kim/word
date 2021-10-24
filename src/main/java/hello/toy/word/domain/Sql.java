package hello.toy.word.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Sql extends BaseTime {

	@Id
	@GeneratedValue
	private Long id;

	private String targetName;
	private String query;

	@OneToMany(mappedBy = "sql")
	private List<SiteSql> siteSqlList = new ArrayList<>();
}
