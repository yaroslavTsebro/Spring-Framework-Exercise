package com.exercise.sfe.repository;

import com.exercise.sfe.entity.Tag;
import java.util.Optional;

public interface TagRepository extends CRDRepository<Tag> {

  Optional<Tag> findByNameLike(String name);

}
