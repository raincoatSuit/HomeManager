package com.github.gleans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * 资源菜单(权限)实体类
 */
@Data
@Entity(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(generator="customGenerationId")
    @GenericGenerator(name="customGenerationId", strategy="com.github.gleans.utils.CustomGenerationId",
            parameters = {@Parameter(name="idPrefix", value="MENU")} )
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconCls;

    private Integer parentId;

    private Boolean enabled;

    @Transient
    private List<Menu> children; //子菜单权限

    @Transient
    private List<Role> roles; //当前权限所要具备的角色
}

