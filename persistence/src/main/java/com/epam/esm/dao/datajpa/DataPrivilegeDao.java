package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataPrivilegeDao extends JpaRepository<Privilege,Long> {
}
