import { Injectable, UnauthorizedException } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { Strategy } from "passport-http-bearer";
import { Account } from "src/account/account.entity";
import { AccountService } from  "src/account/account.service";

@Injectable()
export class TokenStrategy extends PassportStrategy(Strategy, 'token') {

    constructor(private accountService: AccountService){super();}
    async validate(token: string) : Promise<Account>{
        const account = this.accountService.findByToken(token);
        if(account == null) {
            throw new UnauthorizedException();
        }
        return account;
    }
}