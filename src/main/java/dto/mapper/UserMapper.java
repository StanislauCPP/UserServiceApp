package dto.mapper;

import dto.UserDto;
import entity.User;

import java.time.LocalDate;

public class UserMapper {
	public static UserDto toDto(User user) { return new UserDto(user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt()); }

	public static User toEntity(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAge(userDto.getAge());
		user.setCreatedAt(userDto.getCreatedAt());

		return user;
	}
}
