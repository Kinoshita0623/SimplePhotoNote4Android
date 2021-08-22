import { Body, ClassSerializerInterceptor, Controller, Get, Post, Request, UseGuards, UseInterceptors } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { RegisterAccountDTO } from './account.dto';
import { Account } from './account.entity';
import { AccountService } from './account.service';

@Controller('api/accounts')
export class AccountController {

    constructor(private accountService: AccountService){}

    @Post("register")
    @UseInterceptors(ClassSerializerInterceptor)
    async register(@Body() dto: RegisterAccountDTO) : Promise<any>{
        const account = await this.accountService.register(dto)
        return {
            token: account.token,
            account: account
        };
    }

    @Get("me")
    @UseInterceptors(ClassSerializerInterceptor)
    @UseGuards(AuthGuard('token'))
    me(@Request() request) {
        return request.user;
    }

    @Post("signin")
    @UseInterceptors(ClassSerializerInterceptor)
    @UseGuards(AuthGuard('local'))
    async signin(@Request() req) : Promise<any> {
        return {
            token: req.user.token,
            account: req.user
        };
    }
}
