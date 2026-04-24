package com.fundoonotes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
}
