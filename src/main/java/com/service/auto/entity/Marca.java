package com.service.auto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import static com.service.auto.util.DataBaseConstant.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = MARCA_TABLE)
public class Marca implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marca_seq")
    @Column(name = MARCA_ID)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = MARCA_NUME, nullable = false)
    private String nume;

    @Size(max = 100)
    @Column(name = MARCA_TARA_ORIGINE)
    private String taraOrigine;

    @NotNull
    @Value(TRUE)
    @Column(name = MARCA_IS_ENABLED)
    private boolean enabled;

    @Version
    @Column(name = MARCA_VERSION)
    private Integer version;
}
