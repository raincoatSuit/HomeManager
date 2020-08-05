package com.github.gleans.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 租客
 */
@Data
@NoArgsConstructor
@Entity(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "com.github.gleans.utils.CustomGenerationId",
            parameters = {@Parameter(name = "idPrefix", value = "TENANT")})
    private String tenantId;

    @Column(name = "tenant_name",columnDefinition="varchar(100) COMMENT '租客姓名'")
    private String tenantName;

    @Column(name = "phone",columnDefinition="bigint(11) COMMENT '手机号'")
    private Long phone;

}
