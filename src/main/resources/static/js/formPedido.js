document.addEventListener('DOMContentLoaded', function() {
    const itemsContainer = document.getElementById('items-container');
    const addItemButton = document.getElementById('add-item');
    const productosSelects = itemsContainer.querySelectorAll('select[name^="items"]');

    function getProductsHtml() {
        // Obtenemos dinámicamente las opciones del primer select de productos
        // Esto asume que el primer select ya fue cargado por Thymeleaf
        const optionsHtml = productosSelects[0].innerHTML;
        return optionsHtml;
    }

    addItemButton.addEventListener('click', function() {
        const itemCount = itemsContainer.children.length;
        const productsHtml = getProductsHtml();

        const newItemHtml = `
            <div class="item-form">
                <h4>Ítem ${itemCount + 1}</h4>
                <div class="form-group">
                    <label>Producto:</label>
                    <select name="items[${itemCount}].producto.codigoProducto">
                        ${productsHtml}
                    </select>
                </div>
                <div class="form-group">
                    <label>Cantidad:</label>
                    <input type="number" name="items[${itemCount}].cantidad" required min="1">
                </div>
            </div>
        `;
        itemsContainer.insertAdjacentHTML('beforeend', newItemHtml);
    });
});