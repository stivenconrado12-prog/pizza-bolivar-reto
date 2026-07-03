public class itempedido {
    pizza p;
    int cant;
    double subtotal;

    public itempedido(pizza p, int cant) {
        this.p = p;
        this.cant = cant;
        this.subtotal = p.precio;
    }
}
