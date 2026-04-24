package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;
import java.util.List;

public interface NoteService {
    NoteResponseDto createNote(NoteRequestDto requestDto, String token);
    List<NoteResponseDto> getAllNotes(String token);
    NoteResponseDto togglePin(Long noteId, String token);
    NoteResponseDto toggleArchive(Long noteId, String token);
    NoteResponseDto toggleTrash(Long noteId, String token);
}
