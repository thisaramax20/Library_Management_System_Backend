package edu.icet.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    private String bookCode;
    private String title;
    private String isbn;
    private String category;
    private String state;
    private Integer countOfBorrowed;
    private byte[] imageData;
    private String authorId;
    private String authorName;
}
