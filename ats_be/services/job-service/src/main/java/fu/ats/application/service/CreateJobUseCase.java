package fu.ats.application.service;

import fu.ats.api.dto.JobResponse;
import fu.ats.application.command.JobCommand;
import fu.ats.application.port.in.CreateJobPort;
import fu.ats.domain.aggregate.JobAggregate;
import fu.ats.infrastructure.persistence.entity.Job;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase implements CreateJobPort {
    @Override
    public JobResponse execute(JobCommand jobCommand) {

        // map cmd -> aggregate
        JobAggregate jobAggregate = JobAggregate.get(
                jobCommand.id(),
                jobCommand.title(),
                jobCommand.description(),
                jobCommand.departmentId(),
                jobCommand.recruiterId(),
                jobCommand.location(),
                jobCommand.employmentType(),
                jobCommand.workMode(),
                jobCommand.salaryMin(),
                jobCommand.salaryMax(),
                jobCommand.currency(),
                jobCommand.applicationDeadline(),
                jobCommand.skillIds()
        );
        // call draft

        Job jobEntity = jobAggregate.draft();
        return null;
    }
}
