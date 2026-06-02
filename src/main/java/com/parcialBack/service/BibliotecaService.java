package com.parcialBack.service;

import com.parcialBack.model.Biblioteca;
import com.parcialBack.repository.BibliotecaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    public List<Biblioteca> listarBibliotecas() {
        return bibliotecaRepository.findAll();
    }

    public Biblioteca obtenerPorId(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biblioteca no encontrada"));
    }

    public Biblioteca crearBiblioteca(Biblioteca biblioteca) {
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca actualizarBiblioteca(Long id, Biblioteca bibliotecaActualizada) {
        Biblioteca existente = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biblioteca no encontrada"));

        existente.setNombre(bibliotecaActualizada.getNombre());
        existente.setDireccion(bibliotecaActualizada.getDireccion());
        existente.setTelefono(bibliotecaActualizada.getTelefono());
        existente.setResponsable(bibliotecaActualizada.getResponsable());

        return bibliotecaRepository.save(existente);
    }

    public void eliminarBiblioteca(Long id) {
        if (!bibliotecaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Biblioteca no encontrada");
        }
        bibliotecaRepository.deleteById(id);
    }
}
