import { Account } from "src/account/account.entity";
import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity({
    name: 'posts'
})
export class Post {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    text: string;

    @Column()
    author: number;

    @Column()
    accountId: number;

    @ManyToOne(type => Account, account => account.posts)
    account: Account;
}