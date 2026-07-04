import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<Pizza> listarPizzas() {
        List<Pizza> lista = new ArrayList<>();
        String sql = "SELECT id_pizza, nombre, precio, disponible FROM pizzas WHERE disponible = true";

        // Try-with-resources: Cierra automáticamente Connection, PreparedStatement y ResultSet
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) { // Corregido: Sin el punto y coma fatal
                int id = rs.getInt("id_pizza");
                String nom = rs.getString("nombre");
                double precio = rs.getDouble("precio"); // Corregido: Lectura directa como double
                
                Pizza p = new Pizza(id, nom, precio, true);
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pizzas: " + e.getMessage());
        }
        return lista;
    }

    public int guardarCliente(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, telefono, direccion) VALUES (?, ?, ?)";
        int idGenerado = -1;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignación segura mediante marcadores de posición (?)
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getDireccion());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
        }
        return idGenerado;
    }

    public void guardarPedido(Pedido p) {
        String sqlPedido = "INSERT INTO pedidos (id_cliente, total) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedidos (id_pedido, id_pizza, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar()) {
            // Desactivamos el autoCommit para controlar la transacción manualmente
            con.setAutoCommit(false);

            try (PreparedStatement psPed = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psDet = con.prepareStatement(sqlDetalle)) {

                // 1. Insertar la cabecera del Pedido
                psPed.setInt(1, p.getCliente().getId());
                psPed.setDouble(2, p.getTotal());
                psPed.executeUpdate();

                int idPedido = 0;
                try (ResultSet rs = psPed.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPedido = rs.getInt(1);
                    }
                }

                // 2. Insertar los ítems en lote (Batch)
                for (ItemPedido it : p.getItems()) {
                    psDet.setInt(1, idPedido);
                    psDet.setInt(2, it.getPizza().getIdPizza());
                    psDet.setInt(3, it.getCantidad());
                    psDet.setDouble(4, it.getPizza().getPrecio()); // Mantiene el precio histórico de la venta
                    psDet.setDouble(5, it.getSubtotal());
                    psDet.addBatch();
                }
                psDet.executeBatch();

                // Si todo el proceso es exitoso, impactamos la base de datos
                con.commit();
                System.out.println("Pedido y sus detalles guardados correctamente.");

            } catch (SQLException e) {
                // Si algo falla en cualquier punto, deshacemos toda la operación
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Transacción anulada. No se pudo guardar el pedido: " + e.getMessage());
        }
    }

    public void listarPedidos() {
        String sql = "SELECT p.id_pedido, c.nombre, p.total FROM pedidos p INNER JOIN clientes c ON p.id_cliente = c.id_cliente";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Corregido: Mapeo exacto de índices y tipos de datos del SELECT
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                double total = rs.getDouble(3);
                
                System.out.println(id + " - " + nombre + " - $" + total);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }
    }
}