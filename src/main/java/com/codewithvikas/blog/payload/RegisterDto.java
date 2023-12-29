package com.codewithvikas.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
		description = "RegisterDto Model Information"
)
public class RegisterDto {
	@Schema(
			description = "Name of the Registering User"
	)
	private String name;
	
	@Schema(
			description = "Username of the Registering User"
	)
	private String username;
	
	@Schema(
			description = "Email of the Registering User"
	)
	private String email;
	
	@Schema(
			description = "Password of the Registering User"
	)
	private String password;
}
