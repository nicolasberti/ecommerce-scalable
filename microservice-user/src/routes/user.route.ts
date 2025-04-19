import express, { Request, Response, Router } from "express";
import { UserModel } from "../models/user.model";
import { generateToken } from "../utils/jwt";
import bcrypt from "bcryptjs";
import axios from "axios";
import { getProductServiceUrl } from "../config/eurekaclient";
import jwt from "jsonwebtoken";
import dotenv from "dotenv";

dotenv.config();
const JWT_SECRET = process.env.JWT_SECRET || "secret";

export const userRoute = express.Router();

userRoute.post("/register", async (req: Request, res: Response) => {
    try {
        const { username, email, password } = req.body;
    
        if (!username || !email || !password) {
          res.status(400).json({ message: "Faltan datos obligatorios." });
        }
    
        // Verificar si el usuario ya existe
        const existingUser = await UserModel.findOne({ email });
        if (existingUser) {
          res.status(409).json({ message: "El email ya está registrado." });
        }
    
        const hashedPassword = await bcrypt.hash(password, 10);

        // Crear nuevo usuario
        const newUser = new UserModel({ username, email, password: hashedPassword });
        await newUser.save();

        const token = generateToken({ username: newUser.username, email: newUser.email });

        res.status(201).json({ 
          message: "Usuario creado correctamente", 
          data: { user: newUser, token: token } 
        });

      } catch (error) {
        console.error("Error al registrar usuario:", error);
        res.status(500).json({ message: "Error del servidor" });
      }
}
);

userRoute.post("/login", async (req: Request, res: Response) => {
  try {
    const { email, password } = req.body;
    const user = await UserModel.findOne({ email });
    if (!user) {
      res.status(404).json({ message: "Usuario no encontrado" });
      return;
    }

    const passwordMatch = await bcrypt.compare(password, user.password);
    if (!passwordMatch)
      res.status(401).json({ message: "Contraseña incorrecta" });

    const token = generateToken({ id: user?.id, email: user?.email });

    res.json({
      message: "Login exitoso",
      data: {
        token: token,
        user: { username: user.username, email: user.email },
      }
    });
  } catch (error) {
    console.error("Error al iniciar sesión:", error);
    res.status(500).json({ message: "Error del servidor" });
  }
  
});

// validate token
userRoute.get('/auth', (req: Request, res: Response) => {
  let token = '';
  const authHeader = req.headers['authorization'];
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    res.status(401).json({ error: 'Token no proporcionado o inválido' });
  }
  if(authHeader != undefined)
    token = authHeader.split(' ')[1];
  const decoded = jwt.verify(token, JWT_SECRET);
  if(decoded)
    res.status(200).json({ message: 'Token válido' });
  else
    res.status(401).json({ error: 'Token no proporcionado o inválido' });
});

// comunicacion con el microservicio de ordenes
userRoute.get("/orders/:id", (req, res) => {
    
})

// example comunicacion con el microservicio de productos
userRoute.get("/products", async (req, res) => {
  console.log(getProductServiceUrl());
  const response = await axios.get(`${getProductServiceUrl()}/products`);
  console.log("Fetch to /user/products");
  res.json(response.data);
})
