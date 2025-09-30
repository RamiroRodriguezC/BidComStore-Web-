document.addEventListener('DOMContentLoaded', function() {
    const itemsContainer = document.getElementById('items-container');
    const addItemButton = document.getElementById('add-item');

    // Función para obtener las opciones de producto (asume que el primer select ya existe)
    function getProductsHtml() {
        const firstSelect = itemsContainer.querySelector('select[name^="items"]');
        if (firstSelect) {
            // Obtenemos dinámicamente las opciones del primer select de productos
            return firstSelect.innerHTML;
        }
        return ''; // Retorna vacío si no encuentra el primer select
    }

    addItemButton.addEventListener('click', function() {
        const itemCount = itemsContainer.children.length;
        const productsHtml = getProductsHtml();

        // 🚨 IMPORTANTE: Se han agregado las clases de Bootstrap aquí
        const newItemHtml = `
            <div class="item-form p-3 border rounded mb-3">
                <h4 class="h5">Ítem ${itemCount + 1}</h4>
                
                <div class="mb-3">
                    <label class="form-label">Producto:</label>
                    <select name="items[${itemCount}].producto.codigoProducto" class="form-select">
                        ${productsHtml}
                    </select>
                </div>
                
                <div class="mb-3">
                    <label class="form-label">Cantidad:</label>
                    <input type="number" name="items[${itemCount}].cantidad" 
                           class="form-control" required min="1">
                </div>
            </div>
        `;
        
        itemsContainer.insertAdjacentHTML('beforeend', newItemHtml);
    });
});