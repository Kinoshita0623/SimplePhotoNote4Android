import { IsAlpha, IsNotEmpty } from 'class-validator';

export class RegisterAccountDTO {
    @IsNotEmpty()
    @IsAlpha()
    username: string;

    @IsNotEmpty()
    password: string;
}