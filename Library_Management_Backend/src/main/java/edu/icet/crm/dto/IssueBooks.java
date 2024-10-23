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
    private Integer number;
    private LocalDate issuedOn;
    private LocalDate expectedOn;
    private String status;
    private Double fine;
    private String userId;
    private String bookId;
}
