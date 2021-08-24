import { IsNotEmpty } from "class-validator";

export class CreateNoteDTO {
    @IsNotEmpty()
    title: string;

    description?: string;

    @IsNotEmpty()
    fileIds: number[]
}