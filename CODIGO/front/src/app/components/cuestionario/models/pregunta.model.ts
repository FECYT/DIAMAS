export interface Pregunta {
    id: number;
    texto: string;
    ayuda: string;
    categoria: string;
    tipo: 'ADVANCED' | 'BASIC';
    respuesta: boolean | null;
    texto_URL_evidencia: string;
    estado: 'Contestada' | 'Pendiente';
    observaciones: string;
    documentacion?: File;
}
