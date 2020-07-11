package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.Role;
import com.agh.dataminingservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository interface for persisting {@link Role} model to the database, access and retrieving them.
 * It extends Spring Data JPA’s {@link JpaRepository} interface.
 *
 * @author Arkadiusz Michalik
 * @see JpaRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Method retrieving a {@link Role} from the {@link RoleName}
     *
     * @param roleName Role defined by name
     * @return Role defined by RoleName
     */
    Optional<Role> findByName(RoleName roleName);
}
