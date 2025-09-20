package dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@ToString
public class UserDto {
	@NonNull
	private String name;

	@NonNull
	private String email;

	@NonNull
	private Integer age;

	@NonNull
	private LocalDate createdAt;
}
