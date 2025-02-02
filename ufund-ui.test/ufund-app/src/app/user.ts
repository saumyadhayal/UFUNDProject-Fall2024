import { Need } from "./need";

export interface User {
    id: number;
    name: String;
    cart: Need[];
    isAdmin: boolean;
    password: String;
    totalSpent: number;
}