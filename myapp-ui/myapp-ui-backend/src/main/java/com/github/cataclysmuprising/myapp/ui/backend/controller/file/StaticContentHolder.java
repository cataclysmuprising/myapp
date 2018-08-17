/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - StaticContentHolder.java
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
package com.github.cataclysmuprising.myapp.ui.backend.controller.file;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.cataclysmuprising.myapp.common.exception.ContentNotFoundException;
import com.github.cataclysmuprising.myapp.domain.bean.StaticContentBean;
import com.github.cataclysmuprising.myapp.domain.bean.StaticContentBean.FileType;
import com.github.cataclysmuprising.myapp.persistence.service.api.StaticContentService;

@Controller
@RequestMapping(value = "/files/**")
public class StaticContentHolder extends HttpServlet {
    private static final long serialVersionUID = -4968188621073367744L;
    private static final Logger errorLogger = LogManager.getLogger("errorLogs." + StaticContentHolder.class.getName());

    @Autowired
    private StaticContentService contentService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentString = request.getServletPath().substring("/files/".length());
        long contentId = -1l;
        try {
            contentId = Integer.parseInt(contentString);
        } catch (NumberFormatException | ArithmeticException e) {
            // eat this
            errorLogger.debug("Unknown file content.");
            // throw new ServletException("Unknown file content.",
            // e.getCause());
        }
        try {
            StaticContentBean content = contentService.select(contentId);
            if (content == null) {
                throw new ContentNotFoundException("Request content was not found.");
            }
            File file = new File(content.getFilePath());
            if (content.getFileType() == FileType.PDF) {
                response.setContentType("application/pdf; charset=UTF-8");
            } else if (content.getFileType() == FileType.ZIP) {
                response.setContentType("application/zip; charset=UTF-8");
            } else if (content.getFileType() == FileType.IMAGE) {
                response.setContentType("image/jpeg; charset=UTF-8");
            } else {
                response.setHeader("Content-Type", request.getContentType());
            }
            String fileName = URLEncoder.encode(content.getFileName(), "UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);

            Files.copy(file.toPath(), response.getOutputStream());
        } catch (Exception e) {
            errorLogger.error("Requested static-content was not found.");
        }

    }

}
