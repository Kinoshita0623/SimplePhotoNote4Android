import { Controller, Get, Param, Post, Request, StreamableFile, UploadedFile, UseGuards, UseInterceptors } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { FileInterceptor } from '@nestjs/platform-express';
import { createReadStream } from 'fs';
import { FileProperty } from './file.entity';
import { FileService } from './file.service';

@Controller('api/files')
export class FileController {

    constructor(private fileService: FileService) {}

    @Post()
    @UseInterceptors(FileInterceptor('file'))
    @UseGuards(AuthGuard('token'))
    uploadFile(@UploadedFile() file: Express.Multer.File, @Request() request) : Promise<FileProperty> {
        return this.fileService.create(request.user, file)
    }

    @Get(':name')
    async find(@Param('name') name: string) : Promise<StreamableFile>{
        const fileProperty = await this.fileService.findByName(name);
        const file = createReadStream('./files/' + fileProperty.rawName);
        return new StreamableFile(file);
    }

}
