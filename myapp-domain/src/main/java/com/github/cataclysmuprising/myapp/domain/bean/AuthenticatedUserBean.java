package com.github.cataclysmuprising.myapp.domain.bean;

import java.util.List;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AuthenticatedUserBean extends BaseBean {

	private String name;
	private String email;
	private String password;
	private String nrc;
	private Long contentId;
	private Status status;
	private List<Long> roleIds;
	private List<String> roles;

}
