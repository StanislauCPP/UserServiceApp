package dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
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
