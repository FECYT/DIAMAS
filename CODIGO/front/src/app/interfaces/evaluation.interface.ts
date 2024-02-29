import { UserRepository } from './user-repository.interface';
import { QuestionnaireType } from "./questionnaire-type.interface";

export interface Evaluation {
  id: number;
  userRepository: UserRepository;
  lastEdited: Date | null;
  evaluationState: string;
  closeDate: Date | null;
  evaluationGrade: number | null; 
  nDeleteState: number;
  questionnaireType: QuestionnaireType;
}
