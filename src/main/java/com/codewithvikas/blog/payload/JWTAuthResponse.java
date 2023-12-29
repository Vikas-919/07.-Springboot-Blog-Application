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
		description = "JWTAuthResponse Model Information"
)
public class JWTAuthResponse {
	@Schema(
			description = "Access Token"
	)
	private String accessToken;
	
	@Schema(
			description = "Token Type"
	)
	private String tokenType = "Bearer";
	
}
