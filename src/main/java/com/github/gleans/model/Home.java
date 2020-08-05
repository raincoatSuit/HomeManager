package com.github.gleans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "home")
public class Home {

    @Id
    @GeneratedValue(generator="customGenerationId")
    @GenericGenerator(name="customGenerationId", strategy="com.github.gleans.utils.CustomGenerationId",
            parameters = {@Parameter(name="idPrefix", value="HOME")} )
    private String homeId;

    private String address;

}
