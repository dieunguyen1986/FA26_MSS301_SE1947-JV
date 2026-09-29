package fu.ats.infrastructure.adapter;

import fu.ats.api.dto.JobResponse;
import fu.ats.domain.aggregate.JobAggregate;
import fu.ats.domain.repository.JobRepository;
import fu.ats.infrastructure.persistence.DepartmentJpaRepository;
import fu.ats.infrastructure.persistence.JobJpaRepository;
import fu.ats.infrastructure.persistence.entity.Department;
import fu.ats.infrastructure.persistence.entity.Job;
import fu.ats.infrastructure.persistence.mapper.JobPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRepositoryAdapter implements JobRepository {
    private final JobJpaRepository jobJpaRepository;
    private final DepartmentJpaRepository departmentJpaRepository;
    private final JobPersistenceMapper mapper;
    @Override
    public JobAggregate save(JobAggregate aggregate) {
        // Map aggregate to entity


        // Only a reference is needed for the FK, no extra SELECT on departments
        Department department = departmentJpaRepository.findById(aggregate.getDepartmentId()).orElse(null);
        Job saved = jobJpaRepository.saveAndFlush(mapper.toEntity(aggregate, department));
        return mapper.toDomain(saved);
    }
}
