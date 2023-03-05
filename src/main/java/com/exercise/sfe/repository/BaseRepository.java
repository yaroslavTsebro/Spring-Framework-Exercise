package com.exercise.sfe.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;


@AllArgsConstructor
public abstract class BaseRepository {

  public JdbcTemplate jdbcTemplate;
}
