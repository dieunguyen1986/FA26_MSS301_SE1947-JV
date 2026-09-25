package fu.ats.domain.repository;

import fu.ats.api.dto.JobResponse;
import fu.ats.domain.aggregate.JobAggregate;
import fu.ats.infrastructure.persistence.entity.Job;

public interface JobRepository {
    JobAggregate save(JobAggregate aggregate);
}
