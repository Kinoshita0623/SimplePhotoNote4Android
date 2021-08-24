import { Injectable, NotFoundException, UnauthorizedException } from '@nestjs/common';
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
        note.title = dto.title;
        note.description = dto.description;
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

    async find(account: Account, noteId: number): Promise<Note> {
        const note = await this.noteRepository.findOneOrFail({
            id: noteId
        });

        if(!note.isPrivate) {
            return note;
        }
        if(account.id == note.accountId) {
            return note;
        }

        throw new NotFoundException();
    }

    async delete(account: Account, noteId: number){
        const note = await this.find(account, noteId);
        if(note.accountId == account.id) {
            throw new UnauthorizedException();
        }
        this.noteRepository.delete({id: note.id});
    }
}
