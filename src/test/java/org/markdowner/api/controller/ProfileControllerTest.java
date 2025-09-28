package org.markdowner.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.markdowner.api.fixture.Fixtures.profilePathResponseBody;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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

        @Test
        @DisplayName("Deve retornar 200 - OK quando o name é encontrado")
        void shouldReturnOkWhenNameIsFound() throws Exception {
                final var description = "Name: nome encontrado";
                final var name = "Alberto";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                [
                                    {
                                        "id": "019a9ea9-5161-7000-03f1-098e3277407e",
                                        "name": "Alberto Inácio",
                                        "description": "Gerente de produto e inovação."
                                    }
                                ]
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o name é encontrado com case insensitive")
        void shouldReturnOkWhenNameIsFoundIgnoreCase() throws Exception {
                final var description = "Name: nome encontrado ignorando case";
                final var name = "ALBERTO";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                [
                                    {
                                        "id": "019a9ea9-5161-7000-03f1-098e3277407e",
                                        "name": "Alberto Inácio",
                                        "description": "Gerente de produto e inovação."
                                    }
                                ]
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o name é encontrado parcialmente")
        void shouldReturnOkWhenNameIsFoundPartially() throws Exception {
                final var description = "Name: nome encontrado parcialmente";
                final var name = "Alb";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                [
                                    {
                                        "id": "019a9ea9-5161-7000-03f1-098e3277407e",
                                        "name": "Alberto Inácio",
                                        "description": "Gerente de produto e inovação."
                                    }
                                ]
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o name é encontrado com limit customizado")
        void shouldReturnOkWhenNameIsFoundWithCustomLimit() throws Exception {
                final var description = "Name: nome encontrado com limit customizado";
                final var name = "Ana";
                final var limit = "3";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("limit", limit);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                [
                                        {
                                                "id": "019b248e-a961-7000-233d-b99937ef11d4",
                                                "name": "Ana Isidoro",
                                                "description": "Especialista em segurança de aplicações."
                                        },
                                        {
                                                "id": "019926b9-1561-7000-6090-c113559eb471",
                                                "name": "Diana Fernandes",
                                                "description": "Especialista em e-commerce e vendas."
                                        },
                                        {
                                                "id": "019acd02-8d61-7000-dc08-f222fa8c30f4",
                                                "name": "Juliana Rios",
                                                "description": "Estudante de ciência de dados."
                                        }
                                ]
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar erro 404 - NOT_FOUND quando o name não é encontrado")
        void shouldReturnNotFoundWhenNameIsNotFound() throws Exception {
                final var description = "Name: nome não encontrado";
                final var name = "NomeInexistente";
                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isNotFound())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isBlank().as(description);
        }

        @Test
        @DisplayName("Deve retornar 200 - OK quando o name é encontrado com paginação por lastSeenName e lastSeenId")
        void shouldReturnOkWhenNameIsFoundWithPagination() throws Exception {
                final var description = "Name: nome encontrado com paginação";
                final var name = "Ana";
                final var lastSeenName = "Juliana Rios";
                final var lastSeenId = "019acd02-8d61-7000-dc08-f222fa8c30f4";
                final var limit = "3";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("lastSeenName", lastSeenName)
                                .queryParam("lastSeenId", lastSeenId)
                                .queryParam("limit", limit);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                [
                                        {
                                                "id": "0199d5d1-4d61-7000-18f0-9689e06c1745",
                                                "name": "Mariana Paz",
                                                "description": "Estudante de engenharia de dados."
                                        },
                                        {
                                                "id": "019a7050-1561-7000-762c-4de2f6a38808",
                                                "name": "Renata Yamanaka",
                                                "description": "Analista de dados e ciência de dados."
                                        },
                                        {
                                                "id": "01997e45-3161-7000-4774-f18b86a14137",
                                                "name": "Vitor Viana",
                                                "description": "Consultor financeiro e investidor."
                                        }
                                ]
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 400 - BAD_REQUEST quando o lastSeenId não é informado ao lastSeenName estar presente")
        void shouldReturnBadRequestWhenWithoutLastSeenIdWithLastSeenNamePresent() throws Exception {
                final var description = "Name: nome encontrado com paginação por lastSeenName apenas";
                final var name = "Maria";
                final var lastSeenName = "Maria Silva";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("lastSeenName", lastSeenName);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "lastSeenId": [
                                                "não deve ser nulo"
                                        ]
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar 400 - BAD_REQUEST quando o lastSeenName não é informado ao lastSeenId estar presente")
        void shouldReturnBadRequestWhenWithoutLastSeenNameWithLastSeenIdPresent() throws Exception {
                final var description = "Name: nome encontrado com paginação por lastSeenId apenas";
                final var name = "Ana";
                final var lastSeenId = "019a9ea9-5161-7000-03f1-098e327740aa";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("lastSeenId", lastSeenId);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "lastSeenName": [
                                                "não deve ser nulo"
                                        ]
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @Test
        @DisplayName("Deve retornar erro 404 - NOT_FOUND quando o name não é encontrado com paginação")
        void shouldReturnNotFoundWhenNameIsNotFoundWithPagination() throws Exception {
                final var description = "Name: nome não encontrado com paginação";
                final var name = "NomeInexistente";
                final var lastSeenName = "Último Nome";
                final var lastSeenId = "019a9ea9-5161-7000-03f1-098e327740cc";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("lastSeenName", lastSeenName)
                                .queryParam("lastSeenId", lastSeenId);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isNotFound())
                                .andReturn().getResponse().getContentAsString();

                assertThat(response).isBlank().as(description);
        }

        @Test
        @DisplayName("Deve retornar erro 400 - BAD_REQUEST quando lastSeenId é inválido na busca com paginação")
        void shouldReturnBadRequestWhenLastSeenIdIsInvalidWithPagination() throws Exception {
                final var description = "Name: lastSeenId inválido na busca com paginação";
                final var name = "João";
                final var lastSeenName = "João Silva";
                final var invalidLastSeenId = "id-inválido";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("lastSeenName", lastSeenName)
                                .queryParam("lastSeenId", invalidLastSeenId);

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                    "lastSeenId": [
                                        "deve pertencer ao tipo UUID"
                                    ]
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, -1, -10 })
        @DisplayName("Deve retornar erro 400 - BAD_REQUEST quando limit é inválido na busca por nome")
        void shouldReturnBadRequestWhenLimitIsInvalid(final int invalidLimit) throws Exception {
                final var description = "Name: limit inválido (" + invalidLimit + ")";
                final var name = "João";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("limit", String.valueOf(invalidLimit));

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                    "limit": [
                                        "deve ser maior que 0"
                                    ]
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @ParameterizedTest
        @CsvSource(value = {
                        "Nome: número no valor;João123",
                        "Nome: caractere inválido '!';Maria!",
                        "Nome: caractere inválido '@';José@Silva",
                        "Nome: caractere inválido '_';Ana_Souza",
                        "Nome: dois espaços consecutivos;João  Silva",
                        "Nome: dois hífens consecutivos;Ana--Clara",
                        "Nome: dois apóstrofos consecutivos;O''Neill",
                        "Nome: hífen no final;Pedro-",
                        "Nome: apóstrofo no final;Luiz'",
                        "Nome: apenas hífen;-",
                        "Nome: apenas apóstrofo;'",
                        "Nome: letra fora do intervalo permitido (estilizada);𝓙𝓸𝓼é",
                        "Nome: letra fora do intervalo permitido (turca);İlker",
                        "Nome: letra fora do intervalo permitido (polonesa);Łukasz",
                        "Nome: ponto antes de leta;.Pedro Alvares Cabral",
                        "Nome: ponto entre espaços;Pedro . Alvares Cabral",
                        "Nome: ponto após espaço;Pedro .Alvares Cabral"
        }, delimiter = ';')
        @DisplayName("Deve retornar erro 400 - BAD_REQUEST quando lastSeenName é inválido na busca paginada")
        void shouldReturnBadRequestWhenLastSeenNameIsInvalidInPaginatedNameSearch(final String description,
                        final String invalidLastSeenName) throws Exception {

                final var name = "João";

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("name", name)
                                .queryParam("lastSeenName", invalidLastSeenName)
                                .queryParam("lastSeenId", "019a9ea9-5161-7000-03f1-098e3277407e");

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "lastSeenName": [
                                                "deve ser um nome bem formado"
                                        ]
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

        @ParameterizedTest
        @CsvSource(value = {
                        "Nome: número no valor;João123",
                        "Nome: caractere inválido '!';Maria!",
                        "Nome: caractere inválido '@';José@Silva",
                        "Nome: caractere inválido '_';Ana_Souza",
                        "Nome: dois espaços consecutivos;João  Silva",
                        "Nome: dois hífens consecutivos;Ana--Clara",
                        "Nome: dois apóstrofos consecutivos;O''Neill",
                        "Nome: hífen no final;Pedro-",
                        "Nome: apóstrofo no final;Luiz'",
                        "Nome: apenas hífen;-",
                        "Nome: apenas apóstrofo;'",
                        "Nome: letra fora do intervalo permitido (estilizada);𝓙𝓸𝓼é",
                        "Nome: letra fora do intervalo permitido (turca);İlker",
                        "Nome: letra fora do intervalo permitido (polonesa);Łukasz",
                        "Nome: ponto antes de leta;.Pedro Alvares Cabral",
                        "Nome: ponto entre espaços;Pedro . Alvares Cabral",
                        "Nome: ponto após espaço;Pedro .Alvares Cabral"
        }, delimiter = ';')
        @DisplayName("Deve retornar erro 400 - BAD_REQUEST quando lastSeenName é inválido paginação")
        void shouldReturnBadRequestWhenLastSeenNameIsInvalidInPagination(final String description,
                        final String invalidLastSeenName) throws Exception {

                final var request = MockMvcRequestBuilders
                                .get(Routes.PROFILE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("lastSeenName", invalidLastSeenName)
                                .queryParam("lastSeenId", "019a9ea9-5161-7000-03f1-098e3277407e");

                final var response = mockMvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andReturn().getResponse().getContentAsString();

                final String expectedResponse = """
                                {
                                        "lastSeenName": [
                                                "deve ser um nome bem formado"
                                        ]
                                }
                                """;

                assertThat(response).isEqualToIgnoringWhitespace(expectedResponse).as(description);
        }

}
