import { Module } from '@nestjs/common';
import { LocalStrategy } from './local.strategy';
import { TokenStrategy } from './token.strategy';

@Module({
    providers: [LocalStrategy, TokenStrategy]
})
export class AuthModule {}
