import { Account } from "src/account/account.entity";
import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity({
    name: 'files'
})
export class File {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    path: string;

    @Column()
    type: string;

    @Column()
    name: string;

    @Column()
    accountId: number;

    @ManyToOne(type => Account, account => account.files)
    account: Account;
}