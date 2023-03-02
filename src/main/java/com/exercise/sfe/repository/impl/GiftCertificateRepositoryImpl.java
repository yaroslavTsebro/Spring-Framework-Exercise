package com.exercise.sfe.repository.impl;

import com.exercise.sfe.constant.GiftCertificateSQLQueries;
import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.repository.BaseRepository;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.utility.impl.GiftCertificateUpdateQueryBuilder;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
    this.rowMapper = new BeanPropertyRowMapper<>(GiftCertificate.class);
  }

  @Override
  public List<GiftCertificate> getAll() {
    return jdbcTemplate.query(
        GiftCertificateSQLQueries.GET_ALL_GIFT_CERTIFICATES,
        rowMapper
    );
  }

  @Override
  public Optional<GiftCertificate> getById(Long id) {
    return jdbcTemplate.queryForStream(
        GiftCertificateSQLQueries.GET_ALL_GIFT_CERTIFICATES, rowMapper, id).findAny();
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update(
        GiftCertificateSQLQueries.DELETE_GIFT_CERTIFICATE_BY_ID,
        id);
  }

  @Override
  public GiftCertificate create(GiftCertificate giftCertificate) {
    LocalDateTime ldt = LocalDateTime.now();
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
          GiftCertificateSQLQueries.INSERT_GIFT_CERTIFICATE,
          Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, giftCertificate.getName());
      ps.setString(2, giftCertificate.getDescription());
      ps.setBigDecimal(3, giftCertificate.getPrice());
      ps.setInt(4, giftCertificate.getDuration());
      ps.setTimestamp(5, Timestamp.valueOf(ldt));
      ps.setTimestamp(6, Timestamp.valueOf(ldt));
      return ps;
    }, holder);
    Long id = Objects.requireNonNull(holder.getKey()).longValue();
    giftCertificate.setId(id);
    return giftCertificate;
  }

  @Override
  public void update(GiftCertificate previousCert, GiftCertificate wantedCert) {
    var queryBuilder = new GiftCertificateUpdateQueryBuilder();
    String query = queryBuilder.buildUpdateQuery(
        GiftCertificateSQLQueries.UPDATE_GIFT_CERTIFICATE_BASE, previousCert, wantedCert);
    jdbcTemplate.update(query);
  }
}