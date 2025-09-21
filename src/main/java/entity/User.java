package entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.Arrays;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_", nullable = false)
	private String name;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "age", nullable = false)
	private int age;
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
}