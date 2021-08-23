import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Account } from 'src/account/account.entity';
import { FileProperty } from 'src/file/file.entity';
import { Repository } from 'typeorm';
import { CreateNoteDTO } from './note.dto';
import { Note } from './note.entity';

@Injectable()
export class NoteService {

    constructor(
        @InjectRepository(Note) private noteRepository: Repository<Note>, 
        @InjectRepository(FileProperty) private fileRepository: Repository<FileProperty>,
    ) {}

    async create(account: Account, dto: CreateNoteDTO): Promise<Note> {
        const note = new Note();
        note.text = dto.text;
        note.accountId = account.id;
        const files = await this.fileRepository.findByIds(dto.fileIds);
        note.files = files;
        return this.noteRepository.save(note);
    }

    async timeline(account: Account): Promise<Note[]> {
        return this.noteRepository.find({
            where: [
                {
                    isPrivate: false
                },
                {
                    accountId: account.id
                },
            ],
            relations: ["files", "account"],
        })
    }

    async userNotes(account: Account): Promise<Note[]> {
        return await this.noteRepository.find({
            where: {
                accountId: account.id
            },
            relations: ["files", "account"]
        });
    }

}
