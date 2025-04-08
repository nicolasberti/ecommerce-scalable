"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.userRoute = void 0;
const express_1 = __importDefault(require("express"));
const user_model_1 = require("../models/user.model");
const jwt_1 = require("../utils/jwt");
const bcryptjs_1 = __importDefault(require("bcryptjs"));
const fetch = require('node-fetch');
const eurekaclient_1 = require("../config/eurekaclient");
exports.userRoute = express_1.default.Router();
exports.userRoute.post("/register", (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { username, email, password } = req.body;
        if (!username || !email || !password) {
            res.status(400).json({ message: "Faltan datos obligatorios." });
        }
        // Verificar si el usuario ya existe
        const existingUser = yield user_model_1.UserModel.findOne({ email });
        if (existingUser) {
            res.status(409).json({ message: "El email ya está registrado." });
        }
        const hashedPassword = yield bcryptjs_1.default.hash(password, 10);
        // Crear nuevo usuario
        const newUser = new user_model_1.UserModel({ username, email, password: hashedPassword });
        yield newUser.save();
        const token = (0, jwt_1.generateToken)({ username: newUser.username, email: newUser.email });
        res.status(201).json({
            message: "Usuario creado correctamente",
            data: { user: newUser, token: token }
        });
    }
    catch (error) {
        console.error("Error al registrar usuario:", error);
        res.status(500).json({ message: "Error del servidor" });
    }
}));
exports.userRoute.post("/login", (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { email, password } = req.body;
        const user = yield user_model_1.UserModel.findOne({ email });
        if (!user) {
            res.status(404).json({ message: "Usuario no encontrado" });
            return;
        }
        const passwordMatch = yield bcryptjs_1.default.compare(password, user.password);
        if (!passwordMatch)
            res.status(401).json({ message: "Contraseña incorrecta" });
        const token = (0, jwt_1.generateToken)({ id: user === null || user === void 0 ? void 0 : user.id, email: user === null || user === void 0 ? void 0 : user.email });
        res.json({
            message: "Login exitoso",
            data: {
                token: token,
                user: { username: user.username, email: user.email },
            }
        });
    }
    catch (error) {
        console.error("Error al iniciar sesión:", error);
        res.status(500).json({ message: "Error del servidor" });
    }
}));
// comunicacion con el microservicio de ordenes
exports.userRoute.get("/orders/:id", (req, res) => {
});
exports.userRoute.get("/products", (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    console.log((0, eurekaclient_1.getProductServiceUrl)());
    const response = yield fetch(`${(0, eurekaclient_1.getProductServiceUrl)()}/products`);
    const data = yield response.json();
}));
