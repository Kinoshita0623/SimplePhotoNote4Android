import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Account } from 'src/account/account.entity';
import { Repository } from 'typeorm';
import { FileProperty } from './file.entity';

@Injectable()
export class FileService {

    constructor(@InjectRepository(FileProperty) private fileRepository: Repository<FileProperty>) {}

    findByName(name: string) : Promise<FileProperty> {
        return this.fileRepository.findOneOrFail({name: name});
    }

    async create(account: Account, file: Express.Multer.File) : Promise<FileProperty> {
        const fp = new FileProperty(file.mimetype, file.filename, file.originalname, account.id)
        return await this.fileRepository.save(fp)
    }

}
