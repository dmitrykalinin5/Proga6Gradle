package Validaters;

import Tools.Validation;

import java.time.LocalDateTime;

public class CreationDateValidation implements Validation {
    private final LocalDateTime creationDate;

    public CreationDateValidation(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() { return creationDate; }

    @Override
    public boolean validate() { return creationDate != null;}

    @Override
    public String getErrorMessage() {
        return "Ошибка валидации даты";
    }
}
