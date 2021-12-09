package com.epam.esm.dao;

import com.epam.esm.entity.User;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 * @deprecated
 * because project use datajpa since 3 version
 *
 */
@Deprecated(since = "version 3")
public interface UserDao  {
    /**
     * Find all list.
     *
     * @param page the page
     * @return the list with users
     */
    List<User> findAll(Page page);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional of user
     */
    Optional<User> findById(Long id);

}
