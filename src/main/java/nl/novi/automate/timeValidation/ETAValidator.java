package nl.novi.automate.timeValidation;

import nl.novi.automate.model.Ride;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalTime;

public class ETAValidator implements ConstraintValidator<ValidETA, Ride> {
    @Override
    public void initialize(ValidETA futureETA) {
    }

    @Override
    public boolean isValid(Ride ride, ConstraintValidatorContext context) {
        LocalTime departureTime = ride.getDepartureDateTime().toLocalTime();
        LocalTime eta = ride.getEta();

        // Controleer of de ETA na de vertrektijd ligt
        if (eta.isBefore(departureTime) || eta.equals(departureTime)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("De geschatte aankomsttijd is ongeldig. Zorg ervoor dat het later is dan de vertrektijd.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
