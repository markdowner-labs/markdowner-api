package org.markdowner.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.markdowner.api.service.ProfileService;
import org.markdowner.api.util.Routes;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class ProfileControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        @InjectMocks
        private ProfileService profileService;

        @ParameterizedTest
        @CsvSource(value = {
                "Id: identificador inválido;0199125e-z345-719b-8607-7eb3769dbfa6",
                "Id: identificador mal formado;0199125e-z345-719b-8607"
        }, delimiter = ';')
        @DisplayName("Validação do campo Id - casos inválidos")
        void shouldFindProfilesByIdTest(final String description, final String id) throws Exception {
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("id", id);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                assertEquals("{\"id\":\"deve pertencer ao tipo UUID\"}", response, description);
        }

        @ParameterizedTest
        @CsvSource(value = {
                        "Email: letra maiúscula no usuário;Juliana.rios@example.com",
                        "Email: caractere inválido '!' no usuário;juliana!rios@example.com",
                        "Email: espaço no usuário;juliana rios@example.com",
                        "Email: espaço antes do domínio;juliana.rios@ example.com",
                        "Email: espaço antes do TLD;juliana.rios@example .com",
                        "Email: ausência de arroba;juliana.riosexample.com",
                        "Email: dois arrobas;juliana.rios@@example.com",
                        "Email: ausência de TLD;juliana.rios@example",
                        "Email: TLD com menos de duas letras;juliana.rios@example.c",
                        "Email: número no TLD;juliana.rios@example.c0m",
                        "Email: maiúsculas no TLD;juliana.rios@example.CoM",
                        "Email: domínio iniciando com hífen;juliana.rios@-example.com",
                        "Email: dois pontos consecutivos no domínio;juliana.rios@example..com",
                        "Email: domínio terminando com hífen;juliana.rios@example-.com",
                        "Email: caractere acentuado no usuário;juliána.rios@example.com",
                        "Email: vírgula no TLD;juliana.rios@example,com",
                        "Email: caractere inválido '!' no domínio;juliana.rios@exam!ple.com",
                        "Email: espaço no meio do domínio;juliana.rios@exa mple.com",
                        "Email: domínio iniciando com ponto;juliana.rios@.example.com",
                        "Email: endereço de email com mais de 76 caracteres;rprsgefxrayrpaydsncgdxpsnhwpryazfrrbjzkugvqimfidwpcjiyapipyvrehko@example.com",
        }, delimiter = ';')
        @DisplayName("Validação do campo Email - casos inválidos")
        void shouldFindProfilesByEmailTest(final String description, final String email) throws Exception {
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("email", email);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                assertEquals("{\"email\":[\"deve ser um endereço de e-mail bem formado\"]}", response, description);
        }

}
