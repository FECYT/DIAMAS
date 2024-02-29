import {QuestionnaireQuestion} from "./questionnaire-question";

export interface QuestionnaireAnswer{
  id:number,
  questionnaireQuestion: QuestionnaireQuestion,
  observaciones: string,
  answerText: string,
  answer:Boolean,
  nDeleteState: number,
  file: null,
  negativeExtraPoint:number
}
