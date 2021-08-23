import { IsNotEmpty } from "class-validator";

export class CreateNoteDTO {
    @IsNotEmpty()
    text: string;

    @IsNotEmpty()
    fileIds: number[]
}