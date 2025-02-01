export interface SessionDTO {
    id: string;
    idUser?: string;
    loginDateTime?: Date;
    logoutDateTime?: Date;
    token: string;
}