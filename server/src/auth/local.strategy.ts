import { Injectable, UnauthorizedException } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { Strategy } from 'passport-local';
import { AccountService } from "src/account/account.service";
import { Account } from './../account/account.entity';

@Injectable()
export class LocalStrategy extends PassportStrategy(Strategy, 'local') {
    constructor(private accountService: AccountService) {
        super();
    }

    async validate(username: string, password: string): Promise<Account> {
        const account = await this.accountService.findByUserName(username);
        if(account.checkPassword(password)) {
            return account;
        }
        throw new UnauthorizedException();
    }
    
}