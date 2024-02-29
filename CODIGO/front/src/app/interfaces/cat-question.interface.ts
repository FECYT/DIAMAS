import { QuestionnaireType } from "./questionnaire-type.interface";

export interface CatQuestion {
    id: number | undefined;
    categoryType: string | null;
    tooltipType?: string;
    orden: number;
    hasQuestion: boolean;
    ndeleteState: number;
    questionnaireType: QuestionnaireType;
  }
