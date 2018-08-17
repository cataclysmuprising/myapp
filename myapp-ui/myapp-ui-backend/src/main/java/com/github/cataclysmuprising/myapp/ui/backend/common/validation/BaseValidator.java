/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - BaseValidator.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.ui.backend.common.validation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
import com.github.cataclysmuprising.myapp.ui.backend.common.exception.UnSupportedValidationCheckException;
import com.github.cataclysmuprising.myapp.ui.backend.common.util.LocalizedMessageResolver;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BaseValidator implements Validator {

    protected PageMode pageMode;

    private LocalizedMessageResolver messageSource;

    @Autowired
    public void setMessageSource(LocalizedMessageResolver messageSource) {
        this.messageSource = messageSource;
    }

    @SuppressWarnings("rawtypes")
    public boolean validateIsEmpty(FieldValidator fieldValidator) {
        String targetId = fieldValidator.getTargetId();
        Errors errors = fieldValidator.getErrors();
        // no more checking needed
        List<FieldError> fieldErrors = errors.getFieldErrors(targetId);
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return true;
        }
        String displayName = fieldValidator.getDisplayName();
        Object target = fieldValidator.getTarget();
        if (target == null) {
            errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Required", displayName));
            return true;
        } else if (target instanceof String) {
            if (((String) target).isEmpty()) {
                errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Required", displayName));
                return true;
            }
        } else if (target instanceof Collection) {
            if (((Collection) target).isEmpty()) {
                errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.ChooseOne", displayName));
                return true;
            }
        } else if (target instanceof Map) {
            if (((Map) target).isEmpty()) {
                errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.ChooseOne", displayName));
                return true;
            }
        } else if (target.getClass().isArray()) {
            if (((Object[]) target).length == 0) {
                errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.ChooseOne", displayName));
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public boolean validateIsEmpty(Object target) {
        if (target == null) {
            return true;
        } else if (target instanceof String) {
            if (((String) target).isEmpty()) {
                return true;
            }
        } else if (target instanceof Collection) {
            if (((Collection) target).isEmpty()) {
                return true;
            }
        } else if (target instanceof Map) {
            if (((Map) target).isEmpty()) {
                return true;
            }
        } else if (target.getClass().isArray()) {
            if (((Object[]) target).length == 0) {
                return true;
            }
        }
        return false;
    }

    public void validateIsEqual(String targetId, FieldValidator fieldValidator1, FieldValidator fieldValidator2, Errors errors) {
        if (!validateIsEmpty(fieldValidator1) && !validateIsEmpty(fieldValidator2)) {
            if (!fieldValidator1.getTarget().equals(fieldValidator2.getTarget())) {
                errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.DoNotMatch", fieldValidator1.getDisplayName(), fieldValidator2.getDisplayName()));
            }
        }
    }

    public void validateIsValidMinValue(FieldValidator fieldValidator, Number number) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            Object target = fieldValidator.getTarget();
            Errors errors = fieldValidator.getErrors();

            if (target instanceof String) {
                if (target.toString().length() < number.intValue()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Min.String", displayName, number));
                }
            } else if (target instanceof Number) {
                Number inputNumber = (Number) target;
                if (inputNumber.doubleValue() < number.doubleValue()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Min.Number", displayName, number));
                }
            }
        }
    }

    public void validateIsValidMaxValue(FieldValidator fieldValidator, Number number) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            Object target = fieldValidator.getTarget();
            Errors errors = fieldValidator.getErrors();

            if (target instanceof String) {
                if (target.toString().length() > number.intValue()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Max.String", displayName, number));
                }
            } else if (target instanceof Number) {
                Number inputNumber = (Number) target;
                if (inputNumber.doubleValue() > number.doubleValue()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Max.Number", displayName, number));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public void validateIsValidRangeValue(FieldValidator fieldValidator, Number min, Number max) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            Object target = fieldValidator.getTarget();
            Errors errors = fieldValidator.getErrors();
            if (target instanceof String) {
                if (target.toString().length() < min.intValue() || target.toString().length() > max.intValue()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Range.String", displayName, min, max));
                }
            } else if (target instanceof Number) {
                Number number = (Number) target;
                if (number.doubleValue() < min.doubleValue() || number.doubleValue() > max.doubleValue()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.Range.Number", displayName, min, max));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public void validateIsValidDigits(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("[\\-\\+]?\\d+", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidNumber", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    // http://stackoverflow.com/questions/15111420/how-to-check-if-a-string-contains-only-digits-in-java#15111450
    public void validateIsValidUnSignDigits(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("\\d+", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidUnsignNumber", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public void validateIsValidAlphaBatics(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^[a-zA-Z_ ]+$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidAlphabet", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    // include space and underscore
    public void validateIsValidAlphaNumerics(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^[a-zA-Z_0-9 ]+$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidAlphaNumeric", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    // to validate for sql queries
    public void validateIsValidQueryString(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^[a-zA-Z_0-9 \\/\\-\\.\\@]+$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidQueryString", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public void validateIsValidCapitalLetters(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^[A-Z_ \\-]+$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidCapitalLetter", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public void validateIsValidSmallLetters(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^[a-z_ \\-]+$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidSmallLetter", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    // http://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters#15806080
    public void validateIsValidUnicodeCharacters(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^[\\p{L} .'-]+$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidUnicode", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    // http://stackoverflow.com/questions/27938415/regex-for-password-atleast-1-letter-1-number-1-special-character-and-should#27938511
    // and add allowed space for between words
    public void validateIsValidPasswordPattern(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d][A-Za-z \\d!@#$%^&*()_+]{8,}$", target)) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidPassword", displayName));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public boolean validateIsValidPasswordPattern(String target) {
        if (validateIsEmpty(target)) {
            return false;
        }
        return Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d][A-Za-z \\d!@#$%^&*()_+]{8,}$", target);
    }

    // http://stackoverflow.com/questions/8204680/java-regex-email/13013056#13013056
    public void validateIsValidEmail(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                Errors errors = fieldValidator.getErrors();
                String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
                Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
                if (!pattern.matcher(target).find()) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidEmail", target));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public boolean validateIsValidEmail(String target) {
        if (!validateIsEmpty(target)) {
            String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(target).find()) {
                return true;
            }
        }
        return false;
    }

    // http://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java#163410
    public void validateIsValidURL(FieldValidator fieldValidator) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            if (fieldValidator.getTarget() instanceof String) {
                String target = (String) fieldValidator.getTarget();
                try {
                    new URL(target);
                } catch (MalformedURLException e) {
                    Errors errors = fieldValidator.getErrors();
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidURL", target));
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    // http://stackoverflow.com/questions/308122/simple-regular-expression-for-a-decimal-with-a-precision-of-2#308216
    public void validateIsValidFloatingPointNumbers(FieldValidator fieldValidator, int precision) {
        if (!validateIsEmpty(fieldValidator)) {
            String targetId = fieldValidator.getTargetId();
            String displayName = fieldValidator.getDisplayName();
            Errors errors = fieldValidator.getErrors();
            if (fieldValidator.getTarget() instanceof Double) {
                Double target = (Double) fieldValidator.getTarget();
                if (!Pattern.matches("[\\-\\+]?[0-9]+(\\.[0-9]{1," + precision + "})?$", target.toString())) {
                    errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidFloatingPointNumber", displayName));
                }
            } else if (fieldValidator.getTarget() instanceof Float) {
                if (fieldValidator.getTarget() instanceof Double) {
                    Float target = (Float) fieldValidator.getTarget();
                    if (!Pattern.matches("[\\-\\+]?[0-9]+(\\.[0-9]{1," + precision + "})?$", target.toString())) {
                        errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidFloatingPointNumber", displayName, precision));
                    }
                }
            } else {
                throw new UnSupportedValidationCheckException();
            }
        }
    }

    public boolean validatePattern(FieldValidator fieldValidator, String pattern) {

        String targetId = fieldValidator.getTargetId();
        Errors errors = fieldValidator.getErrors();
        String displayName = fieldValidator.getDisplayName();

        if (!validateIsEmpty(fieldValidator)) {
            if (!Pattern.compile(pattern).matcher(String.valueOf(fieldValidator.getTarget())).matches()) {
                errors.rejectValue(targetId, "", messageSource.getMessage("Validation.common.Field.InvalidFormat", displayName));
                return false;
            }
        }

        return true;
    }

    public PageMode getPageMode() {
        return pageMode;
    }

    public void setPageMode(PageMode pageMode) {
        this.pageMode = pageMode;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}
