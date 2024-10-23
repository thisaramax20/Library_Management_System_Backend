package edu.icet.crm.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IssueBookId implements Serializable {
    private Integer userId;
    private Integer bookId;
    private LocalDate issuedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueBookId that = (IssueBookId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId) && Objects.equals(issuedOn, that.issuedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId, issuedOn);
    }
}
