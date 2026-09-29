package fu.ats.api.rest;

import fu.ats.api.dto.CreateJobRequest;
import fu.ats.api.dto.JobResponse;
import fu.ats.application.command.JobCommand;
import fu.ats.application.port.in.CreateJobPort;
import fu.ats.domain.aggregate.JobAggregate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final CreateJobPort createJobPort;

    @PostMapping
    public ResponseEntity<?> createJob(@Valid @RequestBody CreateJobRequest request) {

        JobAggregate jobAggregate = createJobPort.execute(new JobCommand(
                request.title(),
                request.description(),
                request.departmentId(),
                request.recruiterId(),
                request.location(),
                request.employmentType(),
                request.workMode(),
                request.salaryMin(),
                request.salaryMax(),
                request.currency(),
                request.applicationDeadline(),
                request.skillIds()));

        return ResponseEntity.ok(jobAggregate);

    }
}
