import { Module } from '@nestjs/common';
import { AccountModule } from 'src/account/account.module';
import { LocalStrategy } from './local.strategy';
import { TokenStrategy } from './token.strategy';

@Module({
    imports: [AccountModule],
    providers: [LocalStrategy, TokenStrategy]
})
export class AuthModule {}
