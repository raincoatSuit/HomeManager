package com.github.gleans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "com.github.gleans.utils.CustomGenerationId",
            parameters = {@Parameter(name = "idPrefix", value = "ROLE")})
    private Integer id; //角色ID

    private String name; //角色名称

    private String nameZh; //角色中文名称
}

