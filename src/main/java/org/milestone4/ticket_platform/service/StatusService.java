package org.milestone4.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Status;
import org.milestone4.ticket_platform.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
  @Autowired
  private StatusRepository statusRepository;

  public List<Status> findAll() {
    return statusRepository.findAll();
  }

  public List<Status> findByTitle(String name) {
    return statusRepository.findByNameContainingIgnoreCase(name);
  }

  public Status getById(Integer id) {
    Optional<Status> status = statusRepository.findById(id);
    if (status.isEmpty()) {
      throw new IllegalArgumentException("Status not found with id " + id);
    }
    return status.get();
  }

  public Optional<Status> findById(Integer id) {
    return statusRepository.findById(id);
  }

  public Status create (Status status){
    return statusRepository.save(status);
  }

  public Status update(Status status) {
    return statusRepository.save(status);
  }

  public void delete(Status status) {
    statusRepository.delete(status);
  }

  public void deleteById(Integer id) {
    statusRepository.deleteById(id);
  }
}
