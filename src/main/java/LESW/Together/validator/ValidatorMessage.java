package LESW.Together.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
public class ValidatorMessage {

    private final MessageSource messageSource;
    private String DEFAULT_MESSAGE = null;

    public ValidatorMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public String getValidationErrorMessage(String field, BindingResult bindingResult, @Nullable Object[] argument, Locale locale) {
        if (bindingResult.hasFieldErrors(field)) {
            FieldError fieldError = bindingResult.getFieldError(field);
            DEFAULT_MESSAGE = fieldError.getDefaultMessage();
                for (String code : fieldError.getCodes()) {
                    try {
                        String message = messageSource.getMessage(code, argument, locale);
                    } catch (NoSuchMessageException ignored) {

                    }
                }
            }
        return DEFAULT_MESSAGE;
    }
}
