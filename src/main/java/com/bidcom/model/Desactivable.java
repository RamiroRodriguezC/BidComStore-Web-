package com.bidcom.model;

//Interfaz para los objetos desactivables (que podemos eliminar de la base de datos)
//Sirve para luego poder implementar la clase abstracta BaseService
public interface Desactivable {
    void setEnabled(boolean activo);
    boolean isEnabled();
}
