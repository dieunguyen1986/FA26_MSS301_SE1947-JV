package fu.ats.infrastructure.adapter;

import fu.ats.api.dto.JobResponse;
import fu.ats.domain.repository.JobRepository;
import fu.ats.infrastructure.persistence.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobRepositoryAdapter implements JobRepository {
    @Override
    public JobResponse save(Job jobEntity) {
        return null;
    }
}
