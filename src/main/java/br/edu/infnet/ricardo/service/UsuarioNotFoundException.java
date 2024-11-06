package br.edu.infnet.ricardo.service;

public class UsuarioNotFoundException extends Exception {
    public UsuarioNotFoundException(String mensagem) {
        super(mensagem);
    }
}
