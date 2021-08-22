import { Injectable, UnauthorizedException } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { Strategy } from "passport-http-bearer";
import { Account } from "src/account/account.entity";

@Injectable()
export class TokenStrategy extends PassportStrategy(Strategy, 'token') {

    async validate(token: string) : Promise<Account>{
        if(token == 'piyopiyo') {
            const ac = new Account();
            ac.username = 'hoge';
            return ac;
        }
        throw new UnauthorizedException();
    }
}