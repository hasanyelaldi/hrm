package com.hrm.service;

import com.hrm.model.Candidate;
import com.hrm.repository.CandidateRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

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
        return id + " successfully deleted.";
    }

    public String uploadCv(String id, MultipartFile file) throws IOException {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        if(optionalCandidate != null){
            Candidate candidate = optionalCandidate.get();
            DBObject dbObject = new BasicDBObject();
            dbObject.put("fileName", file.getName());
            dbObject.put("contentType", file.getContentType());
            dbObject.put("size", file.getSize());
            dbObject.put("candidateId", "123456");
            ObjectId documentId = gridFsTemplate.store(file.getInputStream(), file.getName(), dbObject);
            candidate.setCv(documentId);
            candidateRepository.save(candidate);
            return file.getName() + " uploaded successfully.";
        }
        return id + "could not found.";
    }

    public String deleteCv(String cvId) {
        gridFsTemplate.delete(new Query(Criteria.where("cv").is(cvId)));
        return cvId + " successfully deleted.";
    }

    public GridFSFile getCv(String cvId) {
        return gridFsTemplate.findOne(new Query(Criteria.where("cv").is(cvId)));
    }
}
