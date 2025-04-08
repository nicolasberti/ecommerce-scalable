import mongoose, { Document } from "mongoose";

export interface User extends Document {
  username: string;
  email: string;
  password: string;
}

const UserSchema = new mongoose.Schema<User>({
  username: { type: String, required: true, unique: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
});

export const UserModel = mongoose.model<User>("User", UserSchema);