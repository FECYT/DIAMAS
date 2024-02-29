export interface Usuario {
    id?: number;
    nombre?: string;
    apellido?: string;
    email?: string;
    recommendationSendEmail?: boolean;
    userId?: string;
    roles?: string;

    rol: ('EVALUADOR' | 'SUPER EVALUADOR' | 'ADMINISTRADOR' | 'RESPONSABLE REPOSITORIO')[];
}
