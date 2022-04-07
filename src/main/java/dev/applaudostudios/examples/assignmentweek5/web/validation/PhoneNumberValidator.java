package dev.applaudostudios.examples.assignmentweek5.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String> {

    private String countryIndicator;
    private int numberLength;

    @Override
    public void initialize(final PhoneNumberValid constraintAnnotation) {
        this.countryIndicator = constraintAnnotation.countryIndicator().trim();
        this.numberLength = constraintAnnotation.numberLength();
    }
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        String cIndicator = phoneNumber.substring(0, this.countryIndicator.length()).trim();
        String number = phoneNumber.substring(this.countryIndicator.length()).trim();
        return cIndicator.equals(this.countryIndicator) &&
                number.length() == this.numberLength &&
                number.matches("^\\d+$");
    }
}
