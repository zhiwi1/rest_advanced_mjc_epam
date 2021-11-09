package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataOrganizationDao extends JpaRepository<Organization,Long> {
}
