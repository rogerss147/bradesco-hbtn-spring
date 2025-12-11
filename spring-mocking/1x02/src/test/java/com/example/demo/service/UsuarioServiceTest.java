package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Usuario usuario = new Usuario(1L, "Roger", "roger@example.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuarioPorId(1L);

        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getNome(), resultado.getNome());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.buscarUsuarioPorId(99L));
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario(null, "Ana", "ana@example.com");
        Usuario usuarioSalvo = new Usuario(2L, "Ana", "ana@example.com");
        when(usuarioRepository.save(usuario)).thenReturn(usuarioSalvo);

        Usuario resultado = usuarioService.salvarUsuario(usuario);

        assertEquals(usuarioSalvo.getId(), resultado.getId());
        assertEquals(usuarioSalvo.getNome(), resultado.getNome());
        assertEquals(usuarioSalvo.getEmail(), resultado.getEmail());
        verify(usuarioRepository).save(usuario);
    }
}
