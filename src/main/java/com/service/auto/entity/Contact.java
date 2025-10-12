package com.service.auto.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "contact_seq", sequenceName = "contact_seq", allocationSize = 1)
@Table(name = "contact")
@Builder
@Entity
public class Contact {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_users")
    private User user;

    @NotNull
    @Column(name = "nume")
    private String nume;

    @NotNull
    @Column(name = "telefon")
    private Integer telefon;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "serie_sasiu")
    private String serieSasiu;

    @Column(name = "descriere")
    private String descriere;



}
