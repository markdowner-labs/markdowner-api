package org.markdowner.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.markdowner.api.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByEmail(final String email);

    @Query(nativeQuery = true, value = """
            select * from profile
            where
                name_purified like '%' || purify(:name) || '%'
            order by (name_purified, id) asc
            limit :limit
            """)
    List<Profile> findByNameContainingIgnoreCase(
            final @Param("limit") int limit,
            final @Param("name") String name);

    @Query(nativeQuery = true, value = """
            select * from profile
            where
                (name_purified, id) > (purify(:lastSeenName), :lastSeenId)
                and
                name_purified like '%' || purify(:name) || '%'
            order by (name_purified, id) asc
            limit :limit
            """)
    List<Profile> findByNameContainingIgnoreCase(
            final @Param("limit") int limit,
            final @Param("lastSeenName") String lastSeenName,
            final @Param("lastSeenId") UUID lastSeenId,
            final @Param("name") String name);

    @Query(nativeQuery = true, value = """
            select * from profile
            order by (name_purified, id) asc
            limit :limit
            """)
    List<Profile> findAll(final @Param("limit") int limit);

    @Query(nativeQuery = true, value = """
            select * from profile
            where (name_purified, id) > (purify(:lastSeenName), :lastSeenId)
            order by (name_purified, id) asc
            limit :limit
            """)
    List<Profile> findAll(
            final @Param("limit") int limit,
            final @Param("lastSeenName") String lastSeenName,
            final @Param("lastSeenId") UUID lastSeenId);

}
