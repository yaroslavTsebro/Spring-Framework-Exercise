package com.exercise.sfe.repository.impl;

import com.exercise.sfe.constant.TagSqlQueries;
import com.exercise.sfe.entity.Tag;
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
public class TagRepositoryImpl implements TagRepository {

  public final JdbcTemplate jdbcTemplate;
  private final RowMapper<Tag> rowMapper;

  public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.rowMapper = new BeanPropertyRowMapper<>(Tag.class);
  }

  @Override
  public List<Tag> getAll() {
    return jdbcTemplate.query(TagSqlQueries.GET_ALL_TAGS, rowMapper);
  }

  @Override
  public Optional<Tag> getById(Long id) {
    return jdbcTemplate.queryForStream(TagSqlQueries.GET_TAG_BY_ID, rowMapper, id).findAny();
  }

  @Override
  @Transactional
  public Tag create(Tag tag) {
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
          TagSqlQueries.INSERT_TAG,
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
        TagSqlQueries.DELETE_TAG_BY_ID,
        id);
  }

  @Override
  public Optional<Tag> getByName(String name) {
    return jdbcTemplate.queryForStream(TagSqlQueries.GET_TAG_BY_NAME_LIKE, rowMapper, name)
        .findFirst();
  }

  @Override
  public List<Tag> getAllByGiftCertificateId(Long id) {
    return jdbcTemplate.query(
        TagSqlQueries.GET_ALL_TAGS_BY_GIFT_CERTIFICATE_ID,
        new Object[]{id},
        rowMapper);
  }

  @Override
  public void connectTagToGiftCertificate(Long tagId, Long gcId) {
    jdbcTemplate.update(TagSqlQueries.CONNECT_TAG_TO_GIFT_CERTIFICATE, gcId, tagId);
  }


  @Override
  public int deleteConnectionToGiftCertificate(Long tagId, Long gcId) {
    return jdbcTemplate.update(
        TagSqlQueries.DELETE_CONNECTION_TO_GIFT_CERTIFICATE,
        tagId, gcId);
  }
}
