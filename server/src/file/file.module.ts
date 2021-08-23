import { Module } from '@nestjs/common';
import { FileService } from './file.service';
import { FileController } from './file.controller';
import { MulterModule } from '@nestjs/platform-express';
import { TypeOrmModule } from '@nestjs/typeorm';
import { FileProperty } from './file.entity';

@Module({
  providers: [FileService],
  controllers: [FileController],
  imports: [
    MulterModule.register({
      dest: './files'
    }),
    TypeOrmModule.forFeature([FileProperty])
  ]
})
export class FileModule {}
