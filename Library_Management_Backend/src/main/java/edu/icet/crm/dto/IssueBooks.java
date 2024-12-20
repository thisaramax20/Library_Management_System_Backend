package edu.icet.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IssueBooks {
    private LocalDate issuedOn;
    private LocalDate expectedOn;
    private String status;
    private String fineStatus;
    private Double fine;
    private String userId;
    private String bookId;
    private String userName;
    private String bookTitle;
}
