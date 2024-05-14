package com.soulcode.elashelp.Models;

public enum Role {

    ADMINISTRADOR("administrador"),
    USUARIO("usuario"),
    TECNICO("tecnico");



    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
