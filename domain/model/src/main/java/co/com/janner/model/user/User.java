package co.com.janner.model.user;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    // Identificadores
    private Long id;
    private String userId;
    // Datos personales
    private String names;
    private String lastName;
    private LocalDate birthDate;
    private String documentType;
    private String documentNumber;
    // Contacto
    private String address;
    private String phoneNumber;
    private String email;
    // Información financiera// Información financiera
    private Double baseSalary;
    private String occupation;
    private String company;
    // Sistema// Sistema
    //private UserRole role;
    //private UserStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    // Métodos de negocio// Métodos de negocio
    public boolean isValidAge() {
        return birthDate.isBefore((LocalDate.now().minusYears(18)));
    }

    public String getFullName() {
        return names + " " + lastName;
    }

    // public boolean canApplyForLoan() {
    //     return status == UserStatus.ACTIVE &&
    //        role == UserRole.SOLICITANTE &&
    //        baseSalary != null && baseSalary > 0;
    // }
}
