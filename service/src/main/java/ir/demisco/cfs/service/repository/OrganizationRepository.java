package ir.demisco.cfs.service.repository;

import ir.demisco.cloud.basic.model.entity.org.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
