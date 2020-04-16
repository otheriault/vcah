package org.vcah.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Player
{
	@Id
	private String id;

	@NotNull
	private String name;

	@NotNull
	@Email
	private String email;

}
