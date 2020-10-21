package com.hrm.controller;

import com.hrm.model.Candidate;
import com.hrm.service.CandidateService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("candidate")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Candidate>> getCandidates(){
        return ResponseEntity.ok(candidateService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Candidate>> getCandidate(@PathVariable String id){
        return ResponseEntity.ok(candidateService.getCandidate(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Candidate> createUser(@RequestBody Candidate candidate) {
        return ResponseEntity.ok(candidateService.saveCandidate(candidate));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Candidate> updateUser(@PathVariable String id, @RequestBody Candidate candidate) {
        Optional<Candidate> candidateData = candidateService.getCandidate(id);
        if (!candidateData.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(candidateService.updateCandidate(id, candidate));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<Candidate> candidateData = candidateService.getCandidate(id);
        if (!candidateData.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(candidateService.deleteCandidate(id));
    }

    @PostMapping("/uploadCv/{id}")
    public ResponseEntity<?> uploadCv(@PathVariable String id, @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(candidateService.uploadCv(id, file));
    }

    @PostMapping("/getCv")
    public ResponseEntity<?> getCv(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(candidateService.getCv(id));
    }
    @PostMapping("/deleteCv/{id}")
    public ResponseEntity<?> deleteCv(@PathVariable String id) {
        return ResponseEntity.ok(candidateService.deleteCv(id));
    }
}
