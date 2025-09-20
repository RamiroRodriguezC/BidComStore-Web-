document.addEventListener('DOMContentLoaded', function () {
    const rolSelect = document.getElementById('rol');
    const clienteFieldsDiv = document.getElementById('cliente-fields');

    // Verifica si el select de roles existe (solo para el admin)
    if (rolSelect) {
        function toggleClienteFields() {
            if (rolSelect.value === 'CLIENTE') {
                clienteFieldsDiv.style.display = 'block';
            } else {
                clienteFieldsDiv.style.display = 'none';
            }
        }
        toggleClienteFields();
        rolSelect.addEventListener('change', toggleClienteFields);
    }
});

