package com.example.EnotesWebApplication.ENotes.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@Data
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit {

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name="created" ,nullable = false,updatable = false)
    private Date created;

    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @Column(name="updated" ,nullable = false)
    private Date updated;

}
