package com.cth.sdm.repository;

import com.cth.sdm.model.DocTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DocTemplateRepository extends JpaRepository<DocTemplate, Long> {
    Optional<DocTemplate> findByTemplateName(String templateName);
}
