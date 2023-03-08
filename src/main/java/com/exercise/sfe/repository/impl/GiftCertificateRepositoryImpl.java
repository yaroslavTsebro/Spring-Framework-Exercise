package com.exercise.sfe.repository.impl;

import com.exercise.sfe.constant.GiftCertificateSqlQueries;
import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.repository.BaseRepository;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.repository.querybuilder.impl.GiftCertificateSearchQueryBuilder;
import com.exercise.sfe.repository.querybuilder.impl.GiftCertificateUpdateQueryBuilder;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificateRepositoryImpl extends BaseRepository implements
    GiftCertificateRepository {

  private RowMapper<GiftCertificate> rowMapper;

  public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
    this.rowMapper = new BeanPropertyRowMapper<>(GiftCertificate.class);
  }

  @Override
  public List<GiftCertificate> getAll() {
    return jdbcTemplate.query(GiftCertificateSqlQueries.GET_ALL_GIFT_CERTIFICATES, rowMapper);
  }

  @Override
  public Optional<GiftCertificate> getById(Long id) {
    return jdbcTemplate.queryForStream(GiftCertificateSqlQueries.GET_GIFT_CERTIFICATE_BY_ID, rowMapper, id)
        .findFirst();
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update(GiftCertificateSqlQueries.DELETE_GIFT_CERTIFICATE_BY_ID, id);
  }

  @Override
  public GiftCertificate create(GiftCertificate giftCertificate) {
    KeyHolder holder = new GeneratedKeyHolder();
    LocalDateTime currTime = LocalDateTime.now();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
          GiftCertificateSqlQueries.INSERT_GIFT_CERTIFICATE,
          Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, giftCertificate.getName());
      ps.setString(2, giftCertificate.getDescription());
      ps.setBigDecimal(3, giftCertificate.getPrice());
      ps.setInt(4, giftCertificate.getDuration());
      ps.setTimestamp(5, Timestamp.valueOf(currTime));
      ps.setTimestamp(6, Timestamp.valueOf(currTime));
      return ps;
    }, holder);
    Map<String, Object> generated = holder.getKeys();
    Long id = Long.parseLong(generated.get("id").toString());
    giftCertificate.setId(id);
    giftCertificate.setCreateDate(currTime);
    giftCertificate.setLastUpdateDate(currTime);
    return giftCertificate;
  }

  @Override
  public void update(GiftCertificate wantedCert) {
    var queryBuilder = new GiftCertificateUpdateQueryBuilder();
    String query = queryBuilder.buildUpdateQuery(
        GiftCertificateSqlQueries.UPDATE_GIFT_CERTIFICATE_BASE, wantedCert);
    var currTime = LocalDateTime.now();
    jdbcTemplate.update(query, currTime);
  }

  @Override
  public List<GiftCertificate> getAll(SearchingSettings settings) {
    String query = GiftCertificateSqlQueries.SEARCH_GIFT_CERTIFICATE_BASE;
    var queryBuilder = new GiftCertificateSearchQueryBuilder();
    query = queryBuilder.buildSearchQueryBySettings(query, settings);
    return jdbcTemplate.query(query, rowMapper);
  }
}
