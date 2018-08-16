package com.github.cataclysmuprising.myapp.ui.backend.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
	@Autowired
	private UserService userService;

	@PostMapping(value = "/datatable/search")
	public ResponseEntity<?> dataTableSearch(@RequestBody UserCriteria criteria) throws BusinessException {
		Map<String, Object> results = new HashMap<>();
		List<UserBean> records = userService.selectList(criteria);
		results.put("iTotalDisplayRecords", userService.selectCounts(criteria));
		results.put("aaData", records);
		return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
	}
}
