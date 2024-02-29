import { Repository } from "./repository.interface";
import { User } from "./user.interface";

export interface AssignedRepository {
    id: number;
    user: User;
    repository: Repository;
    nDeleteState: number;
}
