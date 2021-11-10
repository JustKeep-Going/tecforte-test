package com.tecforte.blog.repository;
import com.tecforte.blog.domain.Blog;
import com.tecforte.blog.domain.Entry;
import com.tecforte.blog.service.dto.EntryDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Entry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    Optional<List<Entry>> findAllByBlog_Id(Long id);
}
