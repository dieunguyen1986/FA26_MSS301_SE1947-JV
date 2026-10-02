package fu.ats.api.rest;

import fu.ats.api.dto.CreateJobRequest;
import fu.ats.api.dto.JobApplicationResponse;
import fu.ats.application.command.JobCommand;
import fu.ats.application.port.in.CreateJobPort;
import fu.ats.domain.aggregate.JobAggregate;
import fu.ats.infrastructure.persistence.entity.JobStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getJob(@Valid @PathVariable("id") UUID id) {

        return ResponseEntity.ok(new JobApplicationResponse(id, "Fullstack Java Dev", JobStatus.PUBLISHED.toString(), LocalDate.of(2026, 12, 31)));
    }
}
