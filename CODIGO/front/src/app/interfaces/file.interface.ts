import { QuestionnaireQuestion } from "./questionnaire-question";

export interface FileInterface {
    id?: number;
    normalizedDocumentName: string;
    fileFormat: string;
    fileSize: number;
    aswerDateTime?: Date;
    fileHash: string;
    /** Esta variable se construye en el backend. */
    filePath?: string;
    nDeleteState: number;
    questionnaireQuestion?: QuestionnaireQuestion;
}

