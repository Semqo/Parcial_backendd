package com.parcialBack.controller;

import com.parcialBack.model.Biblioteca;
import com.parcialBack.service.BibliotecaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bibliotecas")
@CrossOrigin(origins = "*")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public List<Biblioteca> listarBibliotecas() {
        return bibliotecaService.listarBibliotecas();
    }

    @GetMapping("/{id}")
    public Biblioteca obtenerBiblioteca(@PathVariable Long id) {
        return bibliotecaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Biblioteca crearBiblioteca(@RequestBody Biblioteca biblioteca) {
        return bibliotecaService.crearBiblioteca(biblioteca);
    }

    @PutMapping("/{id}")
    public Biblioteca actualizarBiblioteca(@PathVariable Long id, @RequestBody Biblioteca biblioteca) {
        return bibliotecaService.actualizarBiblioteca(id, biblioteca);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarBiblioteca(@PathVariable Long id) {
        bibliotecaService.eliminarBiblioteca(id);
    }
}
