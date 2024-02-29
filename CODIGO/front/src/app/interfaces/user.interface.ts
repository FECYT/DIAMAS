export interface User {
    id?: number;
    lastLogin?: Date | null;
    nDeleteState?: number;
    nombre?: string;
    email?: string;
    rol?: ('ADMINISTRADOR' | 'EDITOR')[];
    apellidos?: string
    ipsp?:string,
    active?:boolean
    password?:string
    acronimo?:string
    url?:string
    afiliacion_institucional?:string
    pais?:string


    dnetId?: string;
    userName?: string;
    recommendationSendEmail?: boolean;
    apellido?: string;
    selected?: boolean;
}

export interface UserPlusRoles{
  user: User
  rolesIds: []
}

