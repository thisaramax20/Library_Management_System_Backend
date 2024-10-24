package edu.icet.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId","bookId","issuedOn"}))
public class IssueBooks {
    @EmbeddedId
    private IssueBookId id;
    private LocalDate expectedOn;
    private String status;
    private Double fine;
    private String fineStatus;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_Id")
    private User user;
    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_Id")
    private Book book;
}
