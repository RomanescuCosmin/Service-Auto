package com.service.auto.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactDto {

    private Long id;
    private Long userId;
    private String nume;
    private Integer telefon;
    private String email;
    private String serieSasiu;
    private String descriere;

}
