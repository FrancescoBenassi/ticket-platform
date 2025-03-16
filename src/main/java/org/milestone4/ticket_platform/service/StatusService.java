package org.milestone4.ticket_platform.service;

import java.util.List;

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
}
