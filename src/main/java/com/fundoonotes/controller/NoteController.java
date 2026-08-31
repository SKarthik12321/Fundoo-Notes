package com.fundoonotes.controller;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;
import com.fundoonotes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(
            @Valid @RequestBody NoteRequestDto requestDto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(requestDto, token));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAllNotes(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(noteService.getAllNotes(token));
    }

    @PutMapping("/{noteId}/pin")
    public ResponseEntity<NoteResponseDto> togglePin(
            @PathVariable Long noteId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(noteService.togglePin(noteId, token));
    }

    @PutMapping("/{noteId}/archive")
    public ResponseEntity<NoteResponseDto> toggleArchive(
            @PathVariable Long noteId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(noteService.toggleArchive(noteId, token));
    }

    @PutMapping("/{noteId}/trash")
    public ResponseEntity<NoteResponseDto> toggleTrash(
            @PathVariable Long noteId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(noteService.toggleTrash(noteId, token));
    }
}
