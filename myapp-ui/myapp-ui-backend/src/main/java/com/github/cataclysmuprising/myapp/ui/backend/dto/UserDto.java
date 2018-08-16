package com.github.cataclysmuprising.myapp.ui.backend.dto;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto extends BaseBean {
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
}
