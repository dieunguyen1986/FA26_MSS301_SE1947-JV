package fu.ats.dto;

import fu.ats.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApplicationResponse {

    private UUID id;

    private UUID jobId;

    private UUID candidateId;

    private UUID cvId;

    private UUID departmentId;

    private Long transferredFrom;

    private Long pipelineStageId;

    private ApplicationStatus status;

    private OffsetDateTime appliedAt;
}

