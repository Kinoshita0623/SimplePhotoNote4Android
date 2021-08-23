import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthModule } from './auth/auth.module';
import { AccountService } from './account/account.service';
import { AccountModule } from './account/account.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { NoteModule } from './note/note.module';
import { FileModule } from './file/file.module';
import { Account } from './account/account.entity';
import { Note } from './note/note.entity';
import { FileProperty } from './file/file.entity';
import { FavoriteService } from './favorite/favorite.service';

@Module({
  imports: [AuthModule, 
    AccountModule, 
    TypeOrmModule.forRoot({
      type: 'mysql' as 'mysql',
      port: 3306,
      username: 'miuser',
      password: 'password',
      database: 'mi-db',
      synchronize: true,
      entities: [Account, Note, FileProperty],
      host: 'mi-db'
    }), NoteModule, FileModule,
    TypeOrmModule.forFeature([Account, FileProperty, Note])
  ],
  controllers: [AppController],
  providers: [AppService, FavoriteService],
})
export class AppModule {}
