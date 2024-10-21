package edu.icet.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bookCode;
    private String title;
    private String isbn;
    private String category;
    private String state;
    private Integer countOfBorrowed;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;
}
