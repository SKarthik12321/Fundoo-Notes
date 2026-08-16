package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;

public interface NoteService {
    NoteResponseDto createNote(NoteRequestDto requestDto, String token);
}
