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
public class User {
    private Integer id;
    private String username;
    private String name;
    private String address;
    private LocalDate dob;
    private String nic;
    private String guardianNic;
    private String password;
    private LocalDate joinedOn;
}
