import { Injectable, UnauthorizedException } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { Strategy } from 'passport-local';
import { Account } from './../account/account.entity';

@Injectable()
export class LocalStrategy extends PassportStrategy(Strategy, 'local') {
    constructor() {
        super();
    }

    async validate(username: string, password: string): Promise<Account> {
        if(username == 'hoge' && password == 'piyo') {
            const ac = new Account();
            ac.username = username;
            return ac;
        }
        throw new UnauthorizedException();
    }
    
}