package com.exercise.sfe.controller;

import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.service.TagService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagController {

  private TagService tagService;

  @GetMapping
  public List<Tag> getAll() {
    return tagService.getAll();
  }

  @GetMapping("/{id}")
  public Tag getById(@PathVariable Long id) {
    return tagService.getById(id);
  }

  @PostMapping
  public Tag create(@RequestBody Tag tag) {
    return tagService.create(tag);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    tagService.deleteById(id);
  }
}
