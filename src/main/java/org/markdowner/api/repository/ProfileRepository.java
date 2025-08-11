package org.markdowner.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.markdowner.api.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByEmail(final String email);

    @Query(nativeQuery = true, value = "select * from profile where name_purified like '%' || purify(?2) || '%' order by (name_purified) asc limit ?1")
    List<Profile> findByNameContainingIgnoreCase(final int limit, final String name);

    @Query(nativeQuery = true, value = "select * from profile where name_purified > purify(?2) and name_purified like '%' || purify(?3) || '%' order by (name_purified) asc limit ?1")
    List<Profile> findByNameContainingIgnoreCase(final int limit, final String lastSeenName, final String name);

}
