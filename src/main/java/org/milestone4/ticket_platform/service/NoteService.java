package org.milestone4.ticket_platform.service;

import java.time.LocalDate;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Note;
import org.milestone4.ticket_platform.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    public Note getById(Integer id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new IllegalArgumentException("Note not found with id " + id);
        }
        return note.get();
    }

    public Note create(Note note) {
        note.setCreationDate(LocalDate.now());
        note.setUpdatedDate(LocalDate.now());
        note.setUser(userService.getCurrentUser());
        return noteRepository.save(note);
    }

    public Note update(Note note) {
        note.setUpdatedDate(LocalDate.now());
        note.setUser(userService.getCurrentUser());
        return noteRepository.save(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteById(Integer id) {
        noteRepository.deleteById(id);
    }
}
