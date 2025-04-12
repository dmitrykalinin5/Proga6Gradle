package Validaters;

import Tools.Validation;

public class IdValidation implements Validation {
    private final Integer id;

    public IdValidation(Integer id) {
        this.id = id;
    }

    public Integer getId() { return id;}

    @Override
    public boolean validate() { return id != null; }

    @Override
    public String getErrorMessage() {
        return "Ошибка в ID";
    }
}
