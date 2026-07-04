public class Pizza {
    // Corregido: Uso de camelCase para estándares de Java
    private int idPizza;
    private String nombre;
    private double precio;
    private boolean disponible;

    // Constructor
    public Pizza(int idPizza, String nombre, double precio, boolean disponible) {
        this.idPizza = idPizza;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
    }

    // Getters y Setters (Esenciales para que ItemPedido y el resto del sistema funcionen)
    public int getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() { // Nota: Para booleanos se suele usar "is" en vez de "get"
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Corregido: Se añade @Override por buena práctica
    @Override
    public String toString() {
        return idPizza + " - " + nombre + " - $" + precio;
    }
}