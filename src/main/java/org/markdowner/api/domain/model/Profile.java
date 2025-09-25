package org.markdowner.api.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.markdowner.api.domain.validation.profile.Birthday;
import org.markdowner.api.domain.validation.profile.Description;
import org.markdowner.api.domain.validation.profile.Email;
import org.markdowner.api.domain.validation.profile.Name;
import org.markdowner.api.domain.validation.profile.Password;
import org.markdowner.api.util.Viewer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(onConstructor_ = @Deprecated)
@AllArgsConstructor(onConstructor_ = @Deprecated)
@Entity
public class Profile implements Serializable {

    @Id
    @JsonView(Viewer.Public.class)
    private UUID id;

    @Name
    @JsonView(Viewer.Public.class)
    private String name;

    @Description
    @JsonView(Viewer.Public.class)
    private String description;

    @Birthday
    @JsonView(Viewer.Protected.class)
    private LocalDate birthday;

    @Email
    @Column(length = 76, unique = true)
    @JsonView(Viewer.Protected.class)
    private String email;

    @Password
    @Column(columnDefinition = "char(60)")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
