import { Institucion } from "./institucion.interface";

export interface Repository {
  id?: number;
  institucion?: Institucion;
  nDeleteState: number;
}
