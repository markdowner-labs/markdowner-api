package org.markdowner.api.repository;

import java.util.UUID;

import org.markdowner.api.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

}
