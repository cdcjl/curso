package com.jlcdc.curso.service;

import com.jlcdc.curso.model.Usuario;
import com.jlcdc.curso.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario crear(Usuario usuario) {
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email ya existe");
        }
        return repository.save(usuario);
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());

        return repository.save(usuarioExistente);
    }
}