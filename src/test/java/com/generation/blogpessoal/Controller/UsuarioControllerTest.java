package com.generation.blogpessoal.Controller;

import com.generation.blogpessoal.Models.Usuario;
import com.generation.blogpessoal.Repository.UsuarioRepository;
import com.generation.blogpessoal.Service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", " "));
    }

    @Test
    @DisplayName("Cadastrar um usuário")
    public void deveCriarUmUsuario() {
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
                new Usuario(0L, "Paulo Antunes", "paulo_antunes@email.com.br", "123454278", "-"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
                "/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {
        usuarioService.cadastrarUsuario(
                new Usuario(0L, "Joana Teste", "joanateste@test.com.br", "Supersenha@123", "-"));

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
                new Usuario(0L, "Joana teste", "joanateste@test.com.br", "Supersenha@123", "-"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
                "/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(
                        new Usuario(0L, "Joana teste", "joanateste@test.com.br", "Supersenha@123", "-"))
                .orElseThrow(() -> new RuntimeException("Erro ao cadastrar usuário"));

        usuarioCadastrado.setNome("Novo Nome");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<>(usuarioCadastrado);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {

        usuarioService.cadastrarUsuario(
                new Usuario(0L, "Joana teste", "joanateste@test.com.br", "Supersenha@123", "-"));

        usuarioService.cadastrarUsuario(
                new Usuario(0L, "Joana teste Dois", "joanateste2@test.com.br", "Supersenha@1232", "-"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}
