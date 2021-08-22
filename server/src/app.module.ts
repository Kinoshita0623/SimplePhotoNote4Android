import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthModule } from './auth/auth.module';
import { AccountService } from './account/account.service';
import { AccountModule } from './account/account.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PostModule } from './post/post.module';
import { FileModule } from './file/file.module';

@Module({
  imports: [AuthModule, 
    AccountModule, 
    TypeOrmModule.forRoot({
      type: 'sqlite',
      database: 'db/sqlitedb.db',
      synchronize: true,
      entities: ['src/**/*.entity.ts']
    }), PostModule, FileModule
  ],
  controllers: [AppController],
  providers: [AppService, AccountService],
})
export class AppModule {}
