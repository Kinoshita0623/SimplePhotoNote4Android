import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthModule } from './auth/auth.module';
import { AccountService } from './account/account.service';
import { AccountModule } from './account/account.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PostModule } from './post/post.module';
import { FileModule } from './file/file.module';
import { Account } from './account/account.entity';
import { Post } from './post/post.entity';
import { FileProperty } from './file/file.entity';

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
      entities: [Account, Post, FileProperty],
      host: 'mi-db'
    }), PostModule, FileModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
