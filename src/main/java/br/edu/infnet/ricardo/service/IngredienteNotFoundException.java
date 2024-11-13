package br.edu.infnet.ricardo.service;

public class IngredienteNotFoundException extends Exception {

    public IngredienteNotFoundException(Long id) {
        super("Ingrediente com id " + id + " não encontrado.");
    }
}
