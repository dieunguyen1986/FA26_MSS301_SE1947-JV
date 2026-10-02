package fu.ats.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApplicationRequest {

    @NotNull(message = "Candidate ID is required")
    private UUID candidateId;

    @NotNull(message = "Job ID is required")
    private UUID jobId;

    @NotNull(message = "CV URL is required")
    private String cvUrl;
}

