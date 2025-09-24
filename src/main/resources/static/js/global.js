/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function confirmarEliminar(boton) {
    // La función 'confirm()' de JavaScript muestra una ventana con 'Aceptar' y 'Cancelar'
    if (confirm("¿Estás seguro de que quieres eliminar este elemento? Esta acción no se puede deshacer.")) {
        // Si el usuario presiona Aceptar, encuentra el formulario padre y lo envía.
        console.log(boton);
        boton.closest('form').submit();
    }
    // Si el usuario presiona Cancelar, no hace nada.
}

