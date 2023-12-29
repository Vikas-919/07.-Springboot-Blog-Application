package com.codewithvikas.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
		description = "CommentDto Model Information"
)
public class CommentDto {
	private long id;
	
	@Schema(
			description = "Commenting User's Name"
	)
	@NotEmpty(message = "Name should not be null or empty.")
	private String name;
	
	@Schema(
			description = "Commenting User's Email"
	)
	@NotEmpty(message = "Email should not be null or empty.")
	@Email
	private String email;
	
	@Schema(
			description = "Comment Body Content"
	)
	@NotEmpty
	@Size(min = 10, message = "Comment body should have atleast 10 characters.")
	private String body;
}
