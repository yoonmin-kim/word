package hello.toy.word.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.toy.word.domain.Sql;
import hello.toy.word.domain.dto.SqlDto;
import hello.toy.word.domain.mapper.ObjectMapper;
import hello.toy.word.repository.SqlRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SqlService {

	private final SqlRepository sqlRepository;
	private final ObjectMapper objectMapper;

	public Sql save(SqlDto sqlDto) {
		return sqlRepository.save(objectMapper.toSql(sqlDto));
	}

	@Transactional(readOnly = true)
	public List<SqlDto> findAll() {
		List<Sql> sqlList = sqlRepository.findAll();
		List<SqlDto> sqlDtoList = new ArrayList<>();
		sqlList.forEach(sql -> sqlDtoList.add(objectMapper.toSqlDto(sql)));
		return sqlDtoList;
	}
}
