import {User} from "./user.interface";

export interface LoginResponse {
    userVO?: User,
    responseCode: number
}
