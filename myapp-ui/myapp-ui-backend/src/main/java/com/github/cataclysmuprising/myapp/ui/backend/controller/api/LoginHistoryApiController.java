package com.github.cataclysmuprising.myapp.ui.backend.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.domain.bean.LoginHistoryBean;
import com.github.cataclysmuprising.myapp.domain.criteria.LoginHistoryCriteria;
import com.github.cataclysmuprising.myapp.persistence.service.api.LoginHistoryService;

@RestController
@RequestMapping("/api/login_history")
public class LoginHistoryApiController {
    @Autowired
    private LoginHistoryService loginHistoryService;

    @RequestMapping(value = "/datatable/search", method = RequestMethod.POST)
    public ResponseEntity<?> dataTableSearch(@RequestBody LoginHistoryCriteria criteria) throws BusinessException {
        Map<String, Object> results = new HashMap<>();
        List<LoginHistoryBean> records = loginHistoryService.selectList(criteria);
        results.put("iTotalDisplayRecords", loginHistoryService.selectCounts(criteria));
        results.put("aaData", records);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
