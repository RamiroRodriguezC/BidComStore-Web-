/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.getElementById('rol').addEventListener('change', function() {
        const clienteFieldsDiv = document.getElementById('cliente-fields');
        if (this.value === 'CLIENTE') {
            clienteFieldsDiv.style.display = 'block';
        } else {
            clienteFieldsDiv.style.display = 'none';
        }
    });


