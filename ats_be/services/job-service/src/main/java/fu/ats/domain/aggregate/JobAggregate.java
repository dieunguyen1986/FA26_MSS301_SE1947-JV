package fu.ats.domain.aggregate;

import fu.ats.infrastructure.persistence.entity.Job;
import fu.ats.infrastructure.persistence.entity.JobStatus;
import fu.ats.infrastructure.persistence.entity.SalaryRange;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Getter
public class JobAggregate {
    private UUID id;
    private Long departmentId;
    private Long recruiterId;
    private String title;

    private String description;
    private String location;

    private SalaryRange salaryRange;
    private JobStatus status;
    private String utmSource;
    private String utmMedium;
    private LocalDate deadline;
    private List<UUID> skillIds;

    private JobAggregate() {

    }

    public static JobAggregate get(UUID id, String title,
                                   String description,
                                   Long departmentId,
                                   Long recruiterId,
                                   String location,
                                   String employmentType,
                                   String workMode,
                                   BigDecimal salaryMin,
                                   BigDecimal salaryMax,
                                   String currency,
                                   LocalDate applicationDeadline,
                                   List<UUID> skillIds) {
        JobAggregate jobAggregate = new JobAggregate();
        jobAggregate.id = id;
        jobAggregate.title = title;
        jobAggregate.description = description;
        jobAggregate.location = location;
        jobAggregate.utmSource = employmentType;
        jobAggregate.utmMedium = workMode;
        jobAggregate.skillIds = skillIds;
        jobAggregate.salaryRange = SalaryRange.salaryRange(salaryMin, salaryMax);
        jobAggregate.recruiterId = recruiterId;
        jobAggregate.departmentId = departmentId;
        jobAggregate.deadline = applicationDeadline;
        return jobAggregate;

    }

    public Job draft() {
        if (skillIds == null) {
            throw new NullPointerException("skillIds is null");
        }

        Job job = new Job();
        job.setTitle(this.title);
        job.setDeadline(this.deadline);
        job.setDescription(this.description);
        job.setLocation(this.location);
        job.setStatus(JobStatus.DRAFT);
        job.setUtmMedium(this.utmMedium);
        job.setUtmSource(this.utmSource);

        return job;
    }

    public void publish() {
        if (this.status != JobStatus.DRAFT) {
            throw new RuntimeException("Job status is not DRAFT");
        }

        this.status = JobStatus.PUBLISHED;
    }
}
