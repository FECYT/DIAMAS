import { Evaluation } from './evaluation.interface';
import { Action } from './action.interface';

export interface EvaluationActionHistory {
  id: number;
  evaluationId: Evaluation;
  actionId: Action;
  lastEdited: Date;
  nDeleteState: number;
}
