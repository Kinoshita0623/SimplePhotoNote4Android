import { Module } from '@nestjs/common';
import { NoteService } from './note.service';
import { NoteController } from './note.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Note } from './note.entity';
import { FileProperty } from 'src/file/file.entity';

@Module({
  providers: [NoteService],
  controllers: [NoteController],
  imports: [TypeOrmModule.forFeature([Note, FileProperty])]
})
export class NoteModule {}
