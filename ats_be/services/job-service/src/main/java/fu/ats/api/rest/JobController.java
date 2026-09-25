package fu.ats.api.rest;

import fu.ats.api.dto.JobResponse;
import fu.ats.application.port.in.CreateJobPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final CreateJobPort createJobPort;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(){

        return ResponseEntity.ok(null);

    }
}
