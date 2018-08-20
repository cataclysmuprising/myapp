/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - AuthenticationController.java
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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class AuthenticationController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, @RequestParam(required = false, name = "error") String error) {
        Locale locale = localeResolver.resolveLocale(request);
        if (error != null) {
            model.addAttribute("messageStyle", "alert-danger");
            if (error.equals("account-disabled")) {
                model.addAttribute("pageMessage", messageSource.getMessage("Serverity.common.auth.message.disabled", null, locale));
            } else if (error.equals("account-locked")) {
                model.addAttribute("pageMessage", messageSource.getMessage("Serverity.common.auth.message.locked", null, locale));
            } else if (error.equals("account-expired")) {
                model.addAttribute("pageMessage", messageSource.getMessage("Serverity.common.auth.message.expired", null, locale));
            } else {
                model.addAttribute("pageMessage", messageSource.getMessage("Serverity.common.auth.message.badCredentials", null, locale));
            }
        }
        return "login_page";
    }
}
