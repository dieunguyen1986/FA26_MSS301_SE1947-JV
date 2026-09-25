package fu.ats.domain.repository;

import fu.ats.api.dto.JobResponse;
import fu.ats.infrastructure.persistence.entity.Job;

public interface JobRepository {
    JobResponse save(Job jobEntity);
}
