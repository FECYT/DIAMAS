import { Evaluation } from "./evaluation.interface";
import { User } from "./user.interface";

export interface AssignedEvaluator {
    id: number;
    user: User;
    dnetId: string;
    evaluation: Evaluation;
    nDeleteState: number;
}
