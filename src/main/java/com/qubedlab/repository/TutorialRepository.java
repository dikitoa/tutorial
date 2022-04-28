package com.qubedlab.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.qubedlab.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	List<Tutorial> findByIsPublished(boolean isPublished);
	List<Tutorial> findByTitleContaining(String title);
}
