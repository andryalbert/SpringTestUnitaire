package com.example.spring.test.unitaire.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_record")
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @NotBlank(message = "Name could not be null")
    private String name;

    @NotBlank(message = "Summary could not be null")
    private String summary;

    private int rating;
}
