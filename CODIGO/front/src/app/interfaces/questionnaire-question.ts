import {Questionnaire} from "./questionnaire.interface";
import {Question, QuestionDTO} from "./question.interface";

export interface QuestionnaireQuestion{
  id: number,
  questionnaire: Questionnaire,
  question: Question,
  nDeleteState: number
}
