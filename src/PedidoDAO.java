import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<pizza> listarPizzas() {
        List<pizza> lista = new ArrayList<pizza>();
        Connection con = ConexionBD.conectar();
        String sql = "SELECT * FROM pizzas WHERE disponible = true";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next());
            {
                int id = rs.getInt("id_pizza");
                String nom = rs.getString("nombre");
                double precio = Double.parseDouble(rs.getString("precio"));
                pizza p = new pizza(id, nom, precio, true);
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return lista;
    }

    public int guardarCliente(cliente c) {
        Connection con = ConexionBD.conectar();
        int idGenerado = -1;
        String sql = "INSERT INTO clientes (nombre,telefono,direccion) VALUES ('" + c.nom + "','" + c.tel + "','" + c.dir + "')";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    public void guardarPedido(pedido p) {
        Connection con = ConexionBD.conectar();
        try {
            String sqlPedido = "INSERT INTO pedidos (id_cliente,total) VALUES (" + p.cli.id + "," + p.total + ")";
            Statement st = con.createStatement();
            st.executeUpdate(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            int idPedido = 0;
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }
            for (itempedido it : p.items) {
                String sqlDet = "INSERT INTO DetallePedidos (id_pedido,id_pizza,Cantidad,subtotal) VALUES (" + idPedido + "," + it.p.id + "," + it.cant + "," + it.subtotal + ")";
                st.executeUpdate(sqlDet);
            }
        } catch (SQLException e) {
            System.out.println("No se pudo guardar");
        }
    }

    public void listarPedidos() {
        Connection con = ConexionBD.conectar();
        String sql = "SELECT p.id_pedido, c.nombre, p.total FROM pedidos p, clientes c WHERE p.id_cliente = c.id_cliente";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                double total = rs.getString(2);
                String nombre = rs.getString(3);
                System.out.println(id + " - " + nombre + " - $" + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
