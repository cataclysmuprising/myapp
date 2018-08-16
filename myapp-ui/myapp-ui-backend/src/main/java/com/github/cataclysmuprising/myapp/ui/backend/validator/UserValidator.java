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
			validateIsEqual("password", new FieldValidator("password", "Password", userDto.getPassword(), errors),
					new FieldValidator("confirmPassword", "Confirm Password", userDto.getConfirmPassword(), errors), errors);

			if (errors.getFieldErrors("email").size() == 0) {
				UserCriteria criteria = new UserCriteria();
				criteria.setEmail(userDto.getEmail());
				Long count = null;
				try {
					count = userService.selectCounts(criteria);
				}
				catch (BusinessException e) {
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