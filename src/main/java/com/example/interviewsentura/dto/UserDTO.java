package com.example.interviewsentura.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @NotBlank(message = "UID is required")
    @Pattern(regexp = ".*\\D.*", message = "UID must contain at least one non-digit character")
    private String uid;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 50, message = "Given name cannot be more than 50 characters")
    private String given_name;

    @Size(max = 50, message = "Middle name cannot be more than 50 characters")
    private String middle_name;

    @Size(max = 100, message = "Name cannot be more than 100 characters")
    private String name;

    @Size(max = 50, message = "Family name cannot be more than 50 characters")
    private String family_name;

    @Size(max = 50, message = "Nickname cannot be more than 50 characters")
    private String nickname;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phone_number;

    @Size(max = 255, message = "Comment cannot be more than 255 characters")
    private String comment;

    private String picture;

    @Size(max = 100, message = "Directory cannot be more than 100 characters")
    private String directory;

    private Map<String, Object> metadata;

    private List<String> tags;

}
