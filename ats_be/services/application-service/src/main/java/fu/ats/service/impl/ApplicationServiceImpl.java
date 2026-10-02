package fu.ats.service.impl;

import fu.ats.client.job.JobClient;
import fu.ats.dto.ApplicationRequest;
import fu.ats.dto.ApplicationResponse;
import fu.ats.dto.JobView;
import fu.ats.entity.Application;
import fu.ats.entity.ApplicationStatus;
import fu.ats.entity.PipelineStage;
import fu.ats.exception.BusinessException;
import fu.ats.repository.ApplicationRepository;
import fu.ats.repository.PipelineStageRepository;
import fu.ats.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    private final PipelineStageRepository pipelineStageRepository;
    private final ApplicationRepository applicationRepository;
    private final JobClient jobClient;

    @Override
    @Transactional
    public ApplicationResponse createApplication(ApplicationRequest request) {
        UUID candidateId = request.getCandidateId();
        UUID jobId = request.getJobId();

        if (applicationRepository.findByCandidateIdAndJobId(candidateId, jobId).isPresent()) {
            throw new BusinessException(1, "Application already exists for this candidate and job");
        }

        // CALL job-service & candidate-service
        // ?? Sync

        JobView jobView = jobClient.findById(jobId);

        if (jobView.getApplicationDeadline().isBefore(LocalDate.now())) {
            throw new BusinessException(2, "Application deadline is before application deadline");
        }

        log.info("Creating application for request {}", jobView);
        List<PipelineStage> pipelineStages =
                pipelineStageRepository.findByDefaultStage(true);

        Application application = new Application();
        application.setJobId(jobId);
        application.setCandidateId(candidateId);
        application.setStatus(ApplicationStatus.SUBMITTED);
        application.setAppliedAt(OffsetDateTime.now());
        application.setPipelineStage(pipelineStages.get(0));

        application.setCvId(UUID.randomUUID());

        Application savedApplication = applicationRepository.save(application);
        return mapToResponse(savedApplication);
    }


    private ApplicationResponse mapToResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJobId())
                .candidateId(application.getCandidateId())
                .cvId(application.getCvId())
                .departmentId(application.getDepartmentId())
                .transferredFrom(application.getTransferredFrom())
                .pipelineStageId(application.getPipelineStage() != null ? application.getPipelineStage().getId() : null)
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}

