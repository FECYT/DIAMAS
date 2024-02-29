import { QuestionnaireType } from "./questionnaire-type.interface";

export interface EvaluationPeriod {
    id: number;
    startDate: Date;
    finishDate: Date;
    status: string;
    description: string;
    deleteState: number;
    questionnaireType: QuestionnaireType;
}
