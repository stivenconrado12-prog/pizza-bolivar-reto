public class ItemPedido {
    private Pizza pizza;
    private int cantidad;
    private double subtotal;

    // Constructor
    public ItemPedido(Pizza pizza, int cantidad) {
        this.pizza = pizza;
        this.cantidad = cantidad;
        // Corregido: Ahora el subtotal calcula el costo real de las pizzas pedidas
        this.subtotal = pizza.getPrecio() * cantidad; 
    }

    // Getters y Setters
    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
        recalcularSubtotal(); // Si cambia la pizza, el subtotal debe actualizarse
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        recalcularSubtotal(); // Si cambia la cantidad, el subtotal debe actualizarse
    }

    public double getSubtotal() {
        return subtotal;
    }

    // Método auxiliar interno para no duplicar la lógica matemática
    private void recalcularSubtotal() {
        if (this.pizza != null) {
            this.subtotal = this.pizza.getPrecio() * this.cantidad;
        }
    }
}