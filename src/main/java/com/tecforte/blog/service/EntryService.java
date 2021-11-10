package com.tecforte.blog.service;

import com.tecforte.blog.domain.Entry;
import com.tecforte.blog.domain.enumeration.EmotionEnum;
import com.tecforte.blog.repository.EntryRepository;
import com.tecforte.blog.service.dto.BlogDTO;
import com.tecforte.blog.service.dto.EntryDTO;
import com.tecforte.blog.service.mapper.EntryMapper;
import com.tecforte.blog.service.util.CheckContainsUtil;
import com.tecforte.blog.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing {@link Entry}.
 */
@Service
@Transactional
public class EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryService.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;

    private final BlogService blogService;

    public EntryService(EntryRepository entryRepository, EntryMapper entryMapper, BlogService blogService) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
        this.blogService = blogService;
    }

    /**
     * Save a entry.
     *
     * @param entryDTO the entity to save.
     * @return the persisted entity.
     */
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Optional<BlogDTO> blog = blogService.findOne(entryDTO.getBlogId());

        if(blog.isPresent() && blog.get().isPositive()){
            CheckContainsUtil.checkContainsNegative(entryDTO.getTitle().toUpperCase(), entryDTO.getContent().toUpperCase());
        }
        else{
            CheckContainsUtil.checkContainsPositive(entryDTO.getTitle().toUpperCase(), entryDTO.getContent().toUpperCase());
        }

        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    /**
     * Get all the entries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entries");
        return entryRepository.findAll(pageable)
            .map(entryMapper::toDto);
    }


    /**
     * Get one entry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntryDTO> findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findById(id)
            .map(entryMapper::toDto);
    }

    /**
     * Delete the entry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.deleteById(id);
    }

    public void deleteOnKeywords(Long id, List<String> keywords){
        log.debug("Request to delete Entry on keywords" + keywords.toString(), " on : {}" + id);

        Optional<List<Entry>> entryList = entryRepository.findAllByBlog_Id(id);
        if(entryList.isPresent()) {
            List<Entry> entries = entryList.get();

            entries = CheckContainsUtil.checkContainsKeywords(keywords, entries);
            for(Entry entry: entries){
                log.debug("Entry Title: " + entry.getTitle());
                log.debug("Entry Content: " + entry.getContent());
            }
            entryRepository.deleteAll(entries);
        }

    }


}
