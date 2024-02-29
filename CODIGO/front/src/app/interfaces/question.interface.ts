import { CatQuestion } from './cat-question.interface';
import {EvaluationPeriod} from "./evaluation-period.interface";

export interface Question {
  id: number;
  catQuestion: CatQuestion;
  title: TitleQuestion;
  orden: number;
  weight: number;
  helpText: string;
  isWritableByEvaluator: number;
  hasUrlText: number;
  hasFileAttach: number;
  nDeleteState: number;
  periodId: number;
  type: string;
  hasNegativeExtraResponse: boolean;
  negativeMessage:string;
}

export interface QuestionDTO {
  id: number;
  catQuestion: CatQuestion;
  title: TitleQuestion;
  orden: number;
  weight: number;
  helpText: string;
  isWritableByEvaluator: number;
  hasUrlText: number;
  hasFileAttach: number;
  nDeleteState: number;
  periodId: EvaluationPeriod;
  type: string;
  negativeExtraPoint:number;
  negativeMessage:string;
  hasNegativeExtraResponse: boolean;
}

export interface TitleQuestion {
  id:number;
  eng:string;
  esp:string;
}


