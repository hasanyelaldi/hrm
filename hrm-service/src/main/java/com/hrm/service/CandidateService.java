package com.hrm.service;

import com.hrm.model.Candidate;
import com.hrm.repository.CandidateRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidate(String id) {
        return candidateRepository.findById(id);
    }

    public Candidate saveCandidate(Candidate candidate) {
        candidate.setCreatedTime(new Date());
        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(String id, Candidate candidate) {
        candidate.setId(id);
        candidate.setUpdatedTime(new Date());
        return candidateRepository.save(candidate);
    }

    public String deleteCandidate(String id) {
        candidateRepository.deleteById(id);
        return id + " successfully deleted";
    }
}
