package com.ktds.member.vo;

import javax.validation.constraints.NotEmpty;

public class MemberVO {
	
	private int id;
	@NotEmpty(message="Eamil은 필수 입력값입니다.")
	private String email;
	@NotEmpty(message="Nickname은필수 입력값입니다.")
	private String nickname;
	@NotEmpty(message="password는 필수 입력값입니다.")
	private String password;
	
	private String registDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

}
