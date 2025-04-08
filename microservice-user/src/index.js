"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const user_route_1 = require("./routes/user.route");
const database_1 = require("./config/database");
const dotenv_1 = __importDefault(require("dotenv"));
const eurekaclient_1 = require("./config/eurekaclient");
dotenv_1.default.config();
const app = (0, express_1.default)();
app.use(express_1.default.json());
app.use("/user", user_route_1.userRoute);
const PORT = process.env.PORT || "9999";
;
(0, database_1.connectDB)().then(() => {
    app.listen(PORT, () => {
        console.log(`🚀 Servidor corriendo en http://localhost:${PORT}`);
    });
    (0, eurekaclient_1.registerWithEureka)();
});
