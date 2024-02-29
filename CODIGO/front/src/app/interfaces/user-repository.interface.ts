import { Repository } from "./repository.interface";
import { User } from "./user.interface";

export interface UserRepository {
    id?: number;
    repository: Repository;
    user?: User;
    nDeleteState?: number;
}