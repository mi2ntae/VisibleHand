package com.it.vh.dict.domain.repository;

import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.entity.DictionaryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {

    Page<Dictionary> findAll(Pageable pageable);

}
