package org.markdowner.api.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.markdowner.api.domain.validation.profile.Birthday;
import org.markdowner.api.domain.validation.profile.Description;
import org.markdowner.api.domain.validation.profile.Email;
import org.markdowner.api.domain.validation.profile.Name;
import org.markdowner.api.domain.validation.profile.Password;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@JsonInclude(JsonInclude.Include.NON_NULL)
@With
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(onConstructor_ = @Deprecated)
@AllArgsConstructor(onConstructor_ = @Deprecated)
@Entity
public class Profile implements Serializable {

    @Id
    private UUID id;

    @Name
    private String name;

    @Description
    private String description;

    @Birthday
    private LocalDate birthday;

    @Email
    @Column(length = 76, unique = true)
    private String email;

    @Password
    @Column(columnDefinition = "char(60)")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
