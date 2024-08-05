package wanted.recruitment.dto.company;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyRegisterRequestDto {
    private List<Long> locations;
    private String name;
}
