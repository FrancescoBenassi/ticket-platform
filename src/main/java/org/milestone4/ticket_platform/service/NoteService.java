package org.milestone4.ticket_platform.service;

import java.time.LocalDate;
import java.util.List;
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

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note getById(Integer id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new IllegalArgumentException("Note not found with id " + id);
        }
        return note.get();
    }

    public List<Note> findByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Note> findById(Integer id) {
        return noteRepository.findById(id);
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
