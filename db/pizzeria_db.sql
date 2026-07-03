-- =========================================================
-- Base de datos: Pizza Bolivar - Gestion de Pedidos
-- Motor: PostgreSQL
--
-- ADVERTENCIA PARA EL RETO:
-- Este script contiene errores de diseno intencionales que
-- el equipo debe encontrar y corregir como parte del reto.
-- =========================================================

DROP TABLE IF EXISTS DetallePedidos;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS pizzas;
DROP TABLE IF EXISTS clientes;

CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion TEXT
);

CREATE TABLE pizzas (
    id_pizza SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio VARCHAR(20),
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INTEGER,
    fecha_pedido TIMESTAMP DEFAULT NOW(),
    total NUMERIC(10,2)
);

CREATE TABLE DetallePedidos (
    id_detalle SERIAL PRIMARY KEY,
    id_pedido INTEGER REFERENCES pedidos(id_pedido),
    id_pizza INTEGER,
    Cantidad INTEGER,
    subtotal NUMERIC(10,2)
);

-- Datos de prueba
INSERT INTO pizzas (nombre, precio, disponible) VALUES
('Margarita', '18000', true),
('Pepperoni', '22000', true),
('Hawaiana', '21000', true),
('Cuatro Quesos', '25000', true),
('Vegetariana', '20000', false);

INSERT INTO clientes (nombre, telefono, direccion) VALUES
('Laura Martinez', '3001234567', 'Calle 45 #12-30, Cartagena'),
('Andres Perez', '3007654321', 'Carrera 8 #20-15, Cartagena');
