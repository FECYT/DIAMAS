import { EvaluationActionHistory } from "./evaluation-action-history.interface";
import { Evaluation } from "./evaluation.interface";


export interface EvaluationWithHistory {
  evaluation: Evaluation;
  history: EvaluationActionHistory[] | null;
}
