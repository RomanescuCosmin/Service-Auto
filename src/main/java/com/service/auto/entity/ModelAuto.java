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
@Table(name = MODEL_TABLE)
@SequenceGenerator(name = "model_seq", sequenceName = "model_seq", allocationSize = 1)
public class ModelAuto implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_seq")
    @Column(name = MODEL_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = MODEL_FK_MARCA, nullable = false)
    private Marca marca;

    @NotNull
    @Size(max = 100)
    @Column(name = MODEL_NUME_MODEL, nullable = false)
    private String numeModel;

    @Column(name = MODEL_AN_FABRICATIE)
    private Integer anFabricatie;

    @NotNull
    @Value(TRUE)
    @Column(name = MODEL_IS_ENABLED)
    private boolean enabled;

    @Version
    @Column(name = MODEL_VERSION)
    private Integer version;
}
