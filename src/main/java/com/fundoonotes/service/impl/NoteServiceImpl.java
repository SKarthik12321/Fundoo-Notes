package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;
import com.fundoonotes.entity.Note;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.NoteNotFoundException;
import com.fundoonotes.exception.UserNotFoundException;
import com.fundoonotes.repository.NoteRepository;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.NoteService;
import com.fundoonotes.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private static final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    private User getUserFromToken(String token) {
        String rawToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        Long userId = tokenUtil.getUserIdFromToken(rawToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private NoteResponseDto mapToResponse(Note note, String message) {
        return new NoteResponseDto(
                note.getId(), note.getTitle(), note.getDescription(),
                note.isPinned(), note.isArchived(), note.isTrashed(),
                note.getCreatedAt(), note.getUpdatedAt(), message);
    }

    @Override
    public NoteResponseDto createNote(NoteRequestDto requestDto, String token) {
        log.info("Creating note");
        User user = getUserFromToken(token);
        Note note = new Note();
        note.setTitle(requestDto.getTitle());
        note.setDescription(requestDto.getDescription());
        note.setUser(user);
        Note saved = noteRepository.save(note);
        log.info("Note created with id: {}", saved.getId());
        return mapToResponse(saved, "Note created successfully");
    }

    @Override
    public List<NoteResponseDto> getAllNotes(String token) {
        log.info("Fetching all notes for user");
        User user = getUserFromToken(token);
        List<Note> notes = noteRepository.findByUserIdAndArchivedFalseAndTrashedFalse(user.getId());
        log.info("Found {} notes for user id: {}", notes.size(), user.getId());
        return notes.stream().map(note -> mapToResponse(note, "Success")).collect(Collectors.toList());
    }

    @Override
    public NoteResponseDto togglePin(Long noteId, String token) {
        log.info("Toggling pin for note id: {}", noteId);
        User user = getUserFromToken(token);
        Note note = noteRepository.findByIdAndUserId(noteId, user.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        note.setPinned(!note.isPinned());
        Note saved = noteRepository.save(note);
        return mapToResponse(saved, "Pin toggled to " + saved.isPinned());
    }

    @Override
    public NoteResponseDto toggleArchive(Long noteId, String token) {
        log.info("Toggling archive for note id: {}", noteId);
        User user = getUserFromToken(token);
        Note note = noteRepository.findByIdAndUserId(noteId, user.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        note.setArchived(!note.isArchived());
        Note saved = noteRepository.save(note);
        return mapToResponse(saved, "Archive toggled to " + saved.isArchived());
    }

    @Override
    public NoteResponseDto toggleTrash(Long noteId, String token) {
        log.info("Toggling trash for note id: {}", noteId);
        User user = getUserFromToken(token);
        Note note = noteRepository.findByIdAndUserId(noteId, user.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        note.setTrashed(!note.isTrashed());
        Note saved = noteRepository.save(note);
        return mapToResponse(saved, "Trash toggled to " + saved.isTrashed());
    }
}
