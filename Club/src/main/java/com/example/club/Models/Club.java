package com.example.club.Models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String nom;
    @Enumerated(EnumType.STRING)
    private TypeClub typeClub;
    String description;
    Boolean status;
    String objectif;
    LocalDate dateCreation;
}
