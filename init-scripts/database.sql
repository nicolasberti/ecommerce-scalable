CREATE TABLE IF NOT EXISTS categoria (
    id INTEGER PRIMARY KEY NOT NULL,
    nombre TEXT NOT NULL,
    descripcion TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS producto (
    id INTEGER PRIMARY KEY NOT NULL,
    nombre TEXT NOT NULL,
    descripcion TEXT NOT NULL,
	precio DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    categoria_id INTEGER NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);
