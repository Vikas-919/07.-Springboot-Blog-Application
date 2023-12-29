package com.codewithvikas.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
		description = "LoginDto Model Information"
)
public class LoginDto {
	@Schema(
			description = "Username or Email Id of the User"
	)
	private String usernameOrEmail;
	
	@Schema(
			description = "Password of the User"
	)
	private String password;
}
