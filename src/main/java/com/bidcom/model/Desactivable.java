/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bidcom.model;

/**
 *
 * @author Ramiro
 */

//Interfaz para los objetos desactivables (que podemos eliminar de la base de datos)

public interface Desactivable {
    void setEnabled(boolean activo);
    boolean isEnabled();
}
