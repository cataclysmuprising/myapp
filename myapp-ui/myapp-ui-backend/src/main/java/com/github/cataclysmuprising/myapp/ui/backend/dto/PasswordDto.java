package com.github.cataclysmuprising.myapp.ui.backend.dto;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordDto extends BaseBean {
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
