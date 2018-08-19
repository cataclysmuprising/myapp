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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.ContentNotFoundException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessageStyle;
import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.HandleAjaxException;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.Loggable;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.ValidateEntity;
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
        user.setStatus(userDto.getStatus());
        userService.createNewUserWithRoles(user, null, getLoginUserId());
        setPageMessage(redirectAttributes, "Success", "Notification.common.Page.SuccessfullyRegistered", PageMessageStyle.SUCCESS, "User");
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) throws BusinessException {
        model.addAttribute("pageMode", PageMode.EDIT);
        UserBean user = userService.select(id);
        if (user == null) {
            throw new ContentNotFoundException();
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setStatus(user.getStatus());
        model.addAttribute("userDto", userDto);
        return "user_edit_page";
    }

    @PostMapping("/{id}/edit")
    @ValidateEntity(validator = UserValidator.class, errorView = "user_edit_page", pageMode = PageMode.EDIT)
    public String edit(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("userDto") UserDto userDto, BindingResult bindResult) throws BusinessException, DuplicatedEntryException {
        UserBean user = new UserBean();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setStatus(userDto.getStatus());
        userService.update(user, getLoginUserId());
        setPageMessage(redirectAttributes, "Success", "Notification.common.Page.SuccessfullyUpdated", PageMessageStyle.SUCCESS, "User");
        return "redirect:/user";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) throws BusinessException {
        model.addAttribute("pageMode", PageMode.VIEW);
        UserBean user = userService.select(id);
        if (user == null) {
            throw new ContentNotFoundException();
        }
        model.addAttribute("user", user);
        return "user_detail_page";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Model model) throws ConsistencyViolationException, BusinessException {
        if (id == getLoginUserId()) {
            setPageMessage(redirectAttributes, "Error", "Notification.User.Remove.Failed", PageMessageStyle.ERROR);
            return "redirect:/user";
        }
        userService.delete(id, getLoginUserId());
        setPageMessage(redirectAttributes, "Success", "Notification.common.Page.SuccessfullyRemoved", PageMessageStyle.SUCCESS, "User");
        return "redirect:/user";
    }

    @PostMapping("/{id}/resetPassword")
    @HandleAjaxException
    public @ResponseBody Map<String, Object> changedPassword(@PathVariable long id, @RequestParam String newPassword, @RequestParam String confirmPassword) throws ServletRequestBindingException, BusinessException, DuplicatedEntryException {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setNewPassword(newPassword);
        passwordDto.setConfirmPassword(confirmPassword);
        Errors errors = new BeanPropertyBindingResult(passwordDto, "passwordDto");
        // validate passwords
        baseValidator.validateIsEqual("newPassword", new FieldValidator("newPassword", "New Password", passwordDto.getNewPassword(), errors), new FieldValidator("confirmPassword", "Confirm Password", passwordDto.getConfirmPassword(), errors), errors);
        baseValidator.validateIsValidPasswordPattern(new FieldValidator("newPassword", "New Password", passwordDto.getNewPassword(), errors));
        baseValidator.validateIsValidPasswordPattern(new FieldValidator("confirmPassword", "Confirm Password", passwordDto.getConfirmPassword(), errors));

        UserBean user = userService.select(id);
        if (user == null) {
            throw new ServletRequestBindingException("Trying to make illegal access for changing password of unknown user with ID =" + id);
        }
        if (errors.hasErrors()) {
            return setAjaxFormFieldErrors(errors, "reset_");
        }
        // update user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.update(user, getLoginUserId());

        // response success
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK);
        return setAjaxPageMessage(response, "Success", "Notification.User.PasswordUpdate.Success", PageMessageStyle.SUCCESS, new Object[] { user.getName() });
    }

}
