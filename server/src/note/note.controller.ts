import { Body, Controller, Delete, Get, Param, Post, Request, UseGuards } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { request } from 'express';
import { CreateNoteDTO } from './note.dto';
import { Note } from './note.entity';
import { NoteService } from './note.service';

@Controller('notes')
export class NoteController {

    constructor(private noteService: NoteService) {}
    @Post()
    @UseGuards(AuthGuard('token'))
    create(@Request() request, @Body() dto: CreateNoteDTO) : Promise<Note>{
        return this.noteService.create(request.user, dto);
    }

    @Get()
    @UseGuards(AuthGuard('token'))
    findAll(@Request() request): Promise<Note[]> {
        return this.noteService.timeline(request.user);
    }

    @Get(':noteId')
    @UseGuards(AuthGuard('token'))
    find(@Request() req, @Param('noteId') noteId: number) : Promise<Note>{
        return this.noteService.find(req.user, noteId);
    }

    @Delete(':noteId')
    @UseGuards(AuthGuard('token'))
    delete(@Request() req, @Param('noteId') noteId: number) {
        return this.noteService.delete(req.user, noteId);
    }

}
