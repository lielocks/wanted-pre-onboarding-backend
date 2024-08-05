package wanted.recruitment.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.recruitment.domain.company.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}