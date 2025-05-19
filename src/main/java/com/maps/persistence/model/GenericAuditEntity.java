package com.maps.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GenericAuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;
    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private User createdBy;
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private User modifiedBy;
}