import java.util.ArrayList;
import java.util.List;

public class Pedido {
    // Atributos privados encapsulados
    private Cliente cliente;
    private List<ItemPedido> items;
    private double total;

    // Constructor: Obliga a que cada pedido nazca con un cliente y una lista limpia
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarItem(ItemPedido it) {
        if (it != null) {
            items.add(it);
            calcularTotal(); // Opcional: Recalcula el total automáticamente al añadir items
        }
    }

    public double calcularTotal() {
        double t = 0;
        // Corregido: El ciclo for-each es elegante, más rápido y 100% libre de IndexOutOfBoundsException
        for (ItemPedido item : items) {
            t += item.getSubtotal(); // Corregido: Usa el método getter seguro
        }
        this.total = t;
        return t;
    }

    public String getNombreCliente() {
        // Corregido: Validación anti-nulos y uso del getter de la clase Cliente
        return (this.cliente != null) ? this.cliente.getNombre() : "Cliente no asignado";
    }

    // Getters y Setters necesarios
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}