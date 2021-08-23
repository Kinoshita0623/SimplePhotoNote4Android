import { Account } from "src/account/account.entity";
import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity({
    name: 'files'
})
export class FileProperty {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    type: string;

    @Column()
    name: string;

    @Column()
    originalname: string;

    @Column()
    accountId: number;

    @ManyToOne(type => Account, account => account.files)
    account: Account;

    constructor(type: string, name: string, orignalname: string, accountId: number) {
        this.type = type;
        this.name = name;
        this.originalname = this.originalname;
        this.accountId = accountId;
    }
}