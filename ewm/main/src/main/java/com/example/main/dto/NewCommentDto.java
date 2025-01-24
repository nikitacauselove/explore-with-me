package com.example.main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewCommentDto {
    @Length(max = 2000)
    @NotBlank
    private String content;

    @NotNull
    private Long event;
}
