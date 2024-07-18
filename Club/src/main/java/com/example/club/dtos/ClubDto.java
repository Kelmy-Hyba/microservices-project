package com.example.club.dtos;

import com.example.club.Models.TypeClub;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {
    private long id;
    private String nom;
    private TypeClub typeClub;
    private String description;
    private Boolean status;
    private String objectif;
    private LocalDate dateCreation;

}
