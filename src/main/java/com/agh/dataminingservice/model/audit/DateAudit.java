package com.agh.dataminingservice.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * DateAudit class will create and update the fields that will be used for audit purposes.
 * JPA’s AuditingEntityListener is used to automatically populate createdAt and updatedAt
 * values when we persist an entity.
 * <p>
 * For enable JPA Auditing, we’ll need to add @EnableJpaAuditing annotation to our
 * configuration class {@link com.agh.dataminingservice.config.AuditingConfig}.
 *
 * @author Arkadiusz Michalik
 * @see Serializable
 * @see AuditingEntityListener
 * @see com.agh.dataminingservice.config.AuditingConfig
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
