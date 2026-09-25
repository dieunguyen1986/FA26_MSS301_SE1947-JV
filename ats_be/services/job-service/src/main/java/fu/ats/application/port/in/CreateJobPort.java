package fu.ats.application.port.in;

import fu.ats.api.dto.JobResponse;
import fu.ats.application.command.JobCommand;

public interface CreateJobPort {
    JobResponse execute(JobCommand jobCommand);
}
