package br.edu.infnet.ricardo.service;

public class UsuarioNotFoundException extends Exception {
    public UsuarioNotFoundException(Long id) {
        super("Usuário com id " + id + " não encontrado.");
    }
}
