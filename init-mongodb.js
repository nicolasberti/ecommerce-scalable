db = connect("mongodb://root:admin@localhost:27017/admin");

// Base de datos: users
db = db.getSiblingDB("users");
db.createCollection("usersCollection"); 

// Base de datos: shoppingcarts
db = db.getSiblingDB("shoppingcarts");
db.createCollection("cartCollection");

// Base de datos: orders
db = db.getSiblingDB("orders");
db.createCollection("ordersCollection");

print("✅ Bases de datos y colecciones creadas con éxito!");
