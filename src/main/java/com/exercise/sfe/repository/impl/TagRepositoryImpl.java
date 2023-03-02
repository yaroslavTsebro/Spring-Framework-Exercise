package com.exercise.sfe.repository.impl;

import com.exercise.sfe.constant.TagSQLQueries;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.repository.BaseRepository;
import com.exercise.sfe.repository.TagRepository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class TagRepositoryImpl extends BaseRepository implements TagRepository {

  private RowMapper<Tag> rowMapper;

  public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
    this.rowMapper = new BeanPropertyRowMapper<>(Tag.class);
  }

  @Override
  public List<Tag> getAll() {
    return jdbcTemplate.query(TagSQLQueries.GET_ALL_TAGS, rowMapper);
  }

  @Override
  public Optional<Tag> getById(Long id) {
    return jdbcTemplate.queryForStream(TagSQLQueries.GET_TAG_BY_ID, rowMapper, id).findAny();
  }

  @Override
  @Transactional
  public Tag create(Tag tag) {
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
          TagSQLQueries.INSERT_TAG,
          Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, tag.getName());
      return ps;
    }, holder);
    Map<String, Object> generated = holder.getKeys();
    Long id = Long.parseLong(generated.get("id").toString());
    tag.setId(id);
    return tag;
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update(
        TagSQLQueries.DELETE_TAG_BY_ID,
        id);
  }

  @Override
  public Optional<Tag> findByNameLike(String name) {
    name = "%" + name + "%";
    return jdbcTemplate.query(TagSQLQueries.GET_TAG_BY_NAME_LIKE, new Object[]{name}, rowMapper)
        .stream()
        .findFirst();
  }
}
