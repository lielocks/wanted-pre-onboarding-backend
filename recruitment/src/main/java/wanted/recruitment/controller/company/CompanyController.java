package wanted.recruitment.controller.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wanted.recruitment.dto.company.CompanyRegisterRequestDto;
import wanted.recruitment.service.company.CompanyService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/company")
public class CompanyController {

    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Long register(@RequestBody CompanyRegisterRequestDto requestDto){
        return companyService.register(requestDto);
    }
}


