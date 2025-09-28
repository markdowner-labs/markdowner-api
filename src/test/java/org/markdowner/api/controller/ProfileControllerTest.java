package org.markdowner.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.markdowner.api.fixture.Fixtures.profilePathResponseBody;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.markdowner.api.util.Routes;
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

        @Test
        @DisplayName("Deve retornar 200 - OK quando o id é encontrado")
        void shouldReturnOkWhenIdIsRegistered() throws Exception {
                final var description = "Id: identificador registrado";
                final var id = "019a9ea9-5161-7000-03f1-098e3277407e";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("id", id);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "id": "019a9ea9-5161-7000-03f1-098e3277407e",
                                        "name": "Alberto Inácio",
                                        "description": "Gerente de produto e inovação."
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o id é nulo")
        void shouldReturnOkWhenIdIsNull() throws Exception {
                final var description = "Id: identificador é nulo";
                final String id = null;
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("id", id);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isEqualToIgnoringWhitespace(profilePathResponseBody).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o id está em branco")
        void shouldReturnOkWhenIdIsBlank() throws Exception {
                final var description = "Id: identificador está em branco";
                final String id = "";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("id", id);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isEqualToIgnoringWhitespace(profilePathResponseBody).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o id não está presente")
        void shouldReturnOkWhenIdIsNotPresent() throws Exception {
                final var description = "Id: identificador não está presente";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isEqualToIgnoringWhitespace(profilePathResponseBody).as(description);
        }

        @ParameterizedTest
        @CsvSource(value = {
                        "Id: identificador inválido (não é um UUID);0199125e-z345-719b-8607-7eb3769dbfa6",
                        "Id: identificador inválido (é incompleto);0199125e-z345-719b-8607"
        }, delimiter = ';')
        @DisplayName("Deve retornar erro 400 - BAD_REQUEST quando o id é inválido")
        void shouldReturnBadRequestWhenIdIsInvalid(final String description, final String id) throws Exception {
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("id", id);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "id": [
                                                "deve pertencer ao tipo UUID"
                                        ]
                                }
                                """;

                assertThat(expectedResponse).isEqualToIgnoringWhitespace(response).as(description);
        }

        @Test
        @DisplayName("Deve retornar erro 404 - NOT_FOUND quando o id não é encontrado")
        void shouldReturnNotFoundWhenIdIsNotRegistered() throws Exception {
                final var description = "Id: identificador não registrado";
                final var id = "11111111-1111-1111-1111-111111111111";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("id", id);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isNotFound())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isBlank().as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o email é encontrado")
        void shouldReturnOkWhenEmailIsRegistered() throws Exception {
                final var description = "Email: email registrado";
                final var email = "alberto.inacio@example.com";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("email", email);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                    "id": "019a9ea9-5161-7000-03f1-098e3277407e",
                                    "name": "Alberto Inácio",
                                    "description": "Gerente de produto e inovação."
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o email é nulo")
        void shouldReturnOkWhenEmailIsNull() throws Exception {
                final var description = "Email: email é nulo";
                final String email = null;
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("email", email);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isEqualToIgnoringWhitespace(profilePathResponseBody).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o email está em branco")
        void shouldReturnOkWhenEmailIsBlank() throws Exception {
                final var description = "Email: email está em branco";
                final String email = "";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("email", email);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isEqualToIgnoringWhitespace(profilePathResponseBody).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o email não está presente")
        void shouldReturnOkWhenEmailIsNotPresent() throws Exception {
                final var description = "Email: email não está presente";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isEqualToIgnoringWhitespace(profilePathResponseBody).as(description);
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
                        "Email: endereço de email com mais de 76 caracteres;rprsgefxrayrpaydsncgdxpsnhwpryazfrrbjzkugvqimfidwpcjiyapipyvrehko@example.com"
        }, delimiter = ';')
        @DisplayName("Deve retornar erro 400 - BAD_REQUEST quando o email é inválido")
        void shouldReturnBadRequestWhenEmailIsInvalid(final String description, final String email) throws Exception {
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("email", email);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "email": [
                                                "deve ser um endereço de e-mail bem formado"
                                        ]
                                }
                                """;

                assertThat(expectedResponse).isEqualToIgnoringWhitespace(response).as(description);
        }

        @Test
        @DisplayName("Deve retornar erro 404 - NOT_FOUND quando o email não é encontrado")
        void shouldReturnNotFoundWhenEmailIsNotRegistered() throws Exception {
                final var description = "Email: email não registrado";
                final var email = "email.nao.registrado@exemplo.com";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("email", email);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isNotFound())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isBlank().as(description);
        }
}
