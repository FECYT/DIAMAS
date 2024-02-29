import { EvaluationPeriod } from "./evaluation-period.interface";
import { Evaluation } from "./evaluation.interface";

export interface Questionnaire {
  id: number;
  creationDate: Date;
  evaluation: Evaluation | null; // Asumiendo que tienes esta interfaz definida en otro lugar
  period: EvaluationPeriod | null; // Asumiendo que tienes esta interfaz definida en otro lugar
  state: string;
  flujoCount: number;
  nDeleteState: number;
}
