package org.milestone4.ticket_platform.service;

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
        return noteRepository.save(note);
    }

    public Note update(Note note) {
        return noteRepository.save(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteById(Integer id) {
        noteRepository.deleteById(id);
    }
}
