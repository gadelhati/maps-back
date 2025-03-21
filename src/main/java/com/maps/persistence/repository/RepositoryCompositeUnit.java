package com.maps.persistence.repository;

import com.maps.persistence.model.CompositePK;
import com.maps.persistence.model.CompositeUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCompositeUnit extends JpaRepository<CompositeUnit, CompositePK> {
    Page<CompositeUnit> findByNameAndNumberOrderByNameAsc(Pageable pageable, int number, String name);
    Page<CompositeUnit> findByNumberAndNameOrderByNumberAsc(Pageable pageable, int number, String name);
    Page<CompositeUnit> findByNameContainingIgnoreCaseOrderByNameAsc(Pageable pageable, String name);
    Page<CompositeUnit> findByValueContainingIgnoreCaseOrderByValueAsc(Pageable pageable, String name);
}