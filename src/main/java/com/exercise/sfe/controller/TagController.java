package com.exercise.sfe.controller;

import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.dto.TagDto;
import com.exercise.sfe.service.TagService;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;

  @GetMapping
  public List<Tag> getAll() {
    return tagService.getAll();
  }

  @GetMapping("/{id}")
  public Tag getById(@PathVariable @Min(1) Long id) {
    return tagService.getById(id);
  }

  @PostMapping
  public Tag create(@RequestBody TagDto tagDto) {
    return tagService.create(tagDto);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable @Min(1) Long id) {
    tagService.deleteById(id);
  }
}
