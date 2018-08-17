/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - UserController.java
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
package com.github.cataclysmuprising.myapp.ui.backend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.ContentNotFoundException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessageStyle;
import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean.Status;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.HandleAjaxException;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.Loggable;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.ValidateEntity;
import com.github.cataclysmuprising.myapp.ui.backend.common.validation.BaseValidator;
import com.github.cataclysmuprising.myapp.ui.backend.common.validation.FieldValidator;
import com.github.cataclysmuprising.myapp.ui.backend.dto.PasswordDto;
import com.github.cataclysmuprising.myapp.ui.backend.dto.UserDto;
import com.github.cataclysmuprising.myapp.ui.backend.validator.UserValidator;

@Controller
@Loggable
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BaseValidator baseValidator;

    @InitBinder("UserDto")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    public void subInit(Model model) {
        setAuthorities(model, "User");
    }

    @GetMapping
    public String home() {
        return "user_home_page";
    }

    @GetMapping("/profile")
    public String profile(Model model) throws BusinessException {
        UserBean user = userService.select(getLoginUserId());
        if (user == null) {
            throw new ContentNotFoundException("User Not found !");
        }
        model.addAttribute("user", user);
        return "user_profile_page";
    }

    @PostMapping("/profile")
    public String profile(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") @Validated UserBean user, BindingResult bindResult) throws BusinessException, DuplicatedEntryException {
        user.setId(getLoginUserId());
        user.setPassword(null);
        user.setStatus(Status.ACTIVE);
        userService.update(user, getLoginUserId());
        setPageMessage(redirectAttributes, "Success", "Notification.User.Profile.SuccessfullyUpdated", PageMessageStyle.SUCCESS);
        return "redirect:/user/profile";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("pageMode", PageMode.CREATE);
        model.addAttribute("userDto", new UserDto());
        return "user_input_page";
    }

    @PostMapping("/add")
    @ValidateEntity(validator = UserValidator.class, errorView = "user_input_page", pageMode = PageMode.CREATE)
    public String add(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("userDto") UserDto userDto, BindingResult bindResult) throws BusinessException, DuplicatedEntryException {
        UserBean user = new UserBean();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setStatus(Status.ACTIVE);
        userService.createNewUserWithRoles(user, null, getLoginUserId());
        setPageMessage(redirectAttributes, "Success", "Notification.common.Page.SuccessfullyRegistered", PageMessageStyle.SUCCESS, "User");
        return "redirect:/user";
    }

    @PostMapping("/changedPassword")
    @HandleAjaxException
    public @ResponseBody Map<String, Object> changedPassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) throws ServletRequestBindingException, BusinessException, DuplicatedEntryException {
        String email = getSignInUserInfo().getEmail();
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword(oldPassword);
        passwordDto.setNewPassword(newPassword);
        passwordDto.setConfirmPassword(confirmPassword);
        Errors errors = new BeanPropertyBindingResult(passwordDto, "passwordDto");
        // validate passwords
        baseValidator.validateIsEqual("newPassword", new FieldValidator("newPassword", "New Password", passwordDto.getNewPassword(), errors), new FieldValidator("confirmPassword", "Confirm Password", passwordDto.getConfirmPassword(), errors), errors);
        baseValidator.validateIsValidPasswordPattern(new FieldValidator("oldPassword", "Old Password", passwordDto.getOldPassword(), errors));
        baseValidator.validateIsValidPasswordPattern(new FieldValidator("newPassword", "New Password", passwordDto.getNewPassword(), errors));
        baseValidator.validateIsValidPasswordPattern(new FieldValidator("confirmPassword", "Confirm Password", passwordDto.getConfirmPassword(), errors));
        // fetch user information with given email
        UserCriteria criteria = new UserCriteria();
        criteria.setEmail(email);
        UserBean user = userService.select(criteria);
        if (user == null) {
            throw new ServletRequestBindingException("Trying to make illegal access for changing password of unknown email=" + email);
        }

        // check is oldpassword correct ?
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            errors.rejectValue("oldPassword", "", "Incorrect old password.Try again !");
        }
        if (errors.hasErrors()) {
            return setAjaxFormFieldErrors(errors, "change_");
        }
        // update user's password
        HashMap<String, Object> updateItems = new HashMap<>();
        updateItems.put("password", passwordEncoder.encode(newPassword));
        userService.update(criteria, updateItems, getLoginUserId());

        // response success
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK);
        return setAjaxPageMessage(response, "Success", "Notification.User.PasswordUpdate.Success", PageMessageStyle.SUCCESS, new Object[] { user.getName() });
    }

}
