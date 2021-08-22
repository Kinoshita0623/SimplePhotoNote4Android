import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { RegisterAccountDTO } from './account.dto';
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

    findByUserName(username: string): Promise<Account> {
        return this.accountRepository.findOneOrFail({username: username})
    }

    register(dto: RegisterAccountDTO) : Promise<Account>{
        const account = new Account(dto.username);
        account.applyPassword(dto.password);
        account.generateToken();
        return this.accountRepository.save(account);
    }
    
    findByToken(token: string): Promise<Account|null> {
        return this.accountRepository.findOne({token: token});
    }
}
