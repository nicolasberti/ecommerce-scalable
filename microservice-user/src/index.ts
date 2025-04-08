import express from "express"
import { userRoute } from "./routes/user.route";
import { connectDB } from "./config/database";
import dotenv from "dotenv";
import { registerWithEureka } from "./config/eurekaclient";

dotenv.config();

const app = express();

app.use(express.json());

app.use("/user", userRoute);

const PORT = process.env.PORT || "9999";;

connectDB().then(() => {
    app.listen(PORT, () => {
      console.log(`🚀 Servidor corriendo en http://localhost:${PORT}`);
    });
    registerWithEureka();
});