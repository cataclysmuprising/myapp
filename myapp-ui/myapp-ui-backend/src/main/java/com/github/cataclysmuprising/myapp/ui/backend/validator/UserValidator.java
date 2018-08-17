/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - UserValidator.java
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
package com.github.cataclysmuprising.myapp.ui.backend.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;
import com.github.cataclysmuprising.myapp.ui.backend.common.util.LocalizedMessageResolver;
import com.github.cataclysmuprising.myapp.ui.backend.common.validation.BaseValidator;
import com.github.cataclysmuprising.myapp.ui.backend.common.validation.FieldValidator;
import com.github.cataclysmuprising.myapp.ui.backend.dto.UserDto;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserValidator extends BaseValidator {

    @Autowired
    private LocalizedMessageResolver messageSource;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserDto userDto = (UserDto) obj;
        validateIsValidRangeValue(new FieldValidator("name", "User Name", userDto.getName(), errors), 5, 50);
        validateIsValidAlphaNumerics(new FieldValidator("name", "User Name", userDto.getName(), errors));

        if (pageMode == PageMode.CREATE) {
            validateIsValidMaxValue(new FieldValidator("email", "Email", userDto.getEmail(), errors), 50);
            validateIsValidEmail(new FieldValidator("email", "Email", userDto.getEmail(), errors));
            validateIsValidPasswordPattern(new FieldValidator("password", "Password", userDto.getPassword(), errors));
            validateIsValidPasswordPattern(new FieldValidator("confirmPassword", "Confirm Password", userDto.getConfirmPassword(), errors));
            validateIsEqual("password", new FieldValidator("password", "Password", userDto.getPassword(), errors), new FieldValidator("confirmPassword", "Confirm Password", userDto.getConfirmPassword(), errors), errors);

            if (errors.getFieldErrors("email").size() == 0) {
                UserCriteria criteria = new UserCriteria();
                criteria.setEmail(userDto.getEmail());
                Long count = null;
                try {
                    count = userService.selectCounts(criteria);
                } catch (BusinessException e) {
                    e.printStackTrace();
                    errors.rejectValue("email", "", messageSource.getMessage("Validation.UserBean.Page.DuplicateEmail", new Object[] { userDto.getEmail() }));
                }
                if (count != null && count > 0) {
                    errors.rejectValue("email", "", messageSource.getMessage("Validation.UserBean.Page.DuplicateEmail", new Object[] { userDto.getEmail() }));
                }
            }

        }

    }

}
