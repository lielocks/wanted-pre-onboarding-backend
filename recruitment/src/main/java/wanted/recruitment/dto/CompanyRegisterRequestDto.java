package wanted.recruitment.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyRegisterRequestDto {
    private List<Long> locations;
    private String name;
}
