import { IsAlpha, IsAlphanumeric, IsNotEmpty } from 'class-validator';

export class RegisterAccountDTO {
    @IsNotEmpty()
    @IsAlphanumeric()
    username: string;

    @IsNotEmpty()
    password: string;
}