package com.example.user.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    long id;

    String nom;
    String prenom;
    Boolean status;
    String email;
    String password;

}
