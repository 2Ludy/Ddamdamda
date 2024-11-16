package com.ddam.damda.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {
	
	private int id;
	private String email;
	private String password;
	private String username;
	private int profileImageId;
	private String role;

}
