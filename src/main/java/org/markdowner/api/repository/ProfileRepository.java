package org.markdowner.api.repository;

import static org.markdowner.api.util.Routes.PROFILE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.markdowner.api.domain.model.Profile;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    @Override
    @NonNull
    @Cacheable(value = PROFILE, sync = true, key = "'findById_' + #id")
    Optional<Profile> findById(@NonNull final UUID id);

    @Cacheable(value = PROFILE, sync = true, key = "'findByEmail_' + #email")
    Optional<Profile> findByEmail(final String email);

    @Query(nativeQuery = true, value = """
            select * from profile
            order by (name_purified, id) asc
            limit :limit
            """)
    @Cacheable(value = PROFILE, sync = true, key = "'findAll_' + #limit")
    List<Profile> findAll(final @Param("limit") int limit);

    @Query(nativeQuery = true, value = """
            select * from profile
            where (name_purified, id) > (purify(:lastSeenName), :lastSeenId)
            order by (name_purified, id) asc
            limit :limit
            """)
    @Cacheable(value = PROFILE, sync = true, key = "'findAll_' + #limit + '_' + #lastSeenName + '_' + #lastSeenId")
    List<Profile> findAll(
            final @Param("limit") int limit,
            final @Param("lastSeenName") String lastSeenName,
            final @Param("lastSeenId") UUID lastSeenId);

    @Query(nativeQuery = true, value = """
            select * from profile
            where
                name_purified like '%' || purify(:name) || '%'
            order by (name_purified, id) asc
            limit :limit
            """)
    @Cacheable(value = PROFILE, sync = true, key = "'findByNameContainingIgnoreCase_' + #limit + '_' + #name")
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
    @Cacheable(value = PROFILE, sync = true, key = "'findByNameContainingIgnoreCase_' + #limit + '_' + #lastSeenName + '_' + #lastSeenId + '_' + #name")
    List<Profile> findByNameContainingIgnoreCase(
            final @Param("limit") int limit,
            final @Param("lastSeenName") String lastSeenName,
            final @Param("lastSeenId") UUID lastSeenId,
            final @Param("name") String name);

}
