import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Account } from './account.entity';

export interface RegsiterAccount {
    username: string;
    password: string
}

@Injectable()
export class AccountService {

    constructor(
        @InjectRepository(Account) private accountRepository: Repository<Account>
    ) {}

    findById(id: number): Promise<Account>{
        return this.accountRepository.findOneOrFail(id)
    }

    findByUserName(username: string): Promise<Account| null> {
        return this.accountRepository.findOne({username: username})
    }

    
}
