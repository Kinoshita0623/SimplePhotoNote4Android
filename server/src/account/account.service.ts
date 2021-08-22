import { Injectable } from '@nestjs/common';

export interface RegsiterAccount {
    username: string;
    password: string
}

@Injectable()
export class AccountService {

    
}
