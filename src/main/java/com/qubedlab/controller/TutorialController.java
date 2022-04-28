package com.qubedlab.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qubedlab.model.Tutorial;
import com.qubedlab.repository.TutorialRepository;


@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;
	
	//POST /api/tutorials create new Tutorial
	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> add(@RequestBody Tutorial tut) {
		try {
			Tutorial added = tutorialRepository
					.save(new Tutorial(tut.getTitle(), tut.getDescription(), false));
			return new ResponseEntity<>(added, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//GET /api/tutorials retrieve all Tutorials
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> findAll() {
		
			List<Tutorial> allTutorials = new ArrayList<Tutorial>();
				tutorialRepository.findAll().forEach(allTutorials::add);

			if (allTutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(allTutorials, HttpStatus.OK);
		
	}

	//GET /api/tutorials/:id retrieve a Tutorial by :id
	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		Optional<Tutorial> data = tutorialRepository.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	//PUT /api/tutorials/:id update a Tutorial by :id
	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> update(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

		System.out.println(tutorial.toString());
		
		if (tutorialData.isPresent()) {
			Tutorial updated = tutorialData.get();
			updated.setTitle(tutorial.getTitle());
			updated.setDescription(tutorial.getDescription());
			updated.setPublished(tutorial.isPublished());
			return new ResponseEntity<>(tutorialRepository.save(updated), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//DELETE /api/tutorials/:id delete a Tutorial by :id
	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		    //its not recommended to delete, its supposed to be an update
		    //but i have deleted since its an interview requirement
		    tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

	//DELETE /api/tutorials delete all Tutorials
	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAll() {
		
		tutorialRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	//GET /api/tutorials/published find all published Tutorials
	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished() {

		  List<Tutorial> tutorials = tutorialRepository.findByIsPublished(true);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		
	}

}
