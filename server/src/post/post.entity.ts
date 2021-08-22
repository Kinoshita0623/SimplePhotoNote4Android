import { Account } from "src/account/account.entity";
import { FileProperty } from "src/file/file.entity";
import { Column, Entity, JoinTable, ManyToMany, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

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

    @ManyToMany(type => FileProperty)
    @JoinTable({
        name: 'post_files',
        joinColumn: {
            name: 'post_id',
            referencedColumnName: 'id'
        },
        inverseJoinColumn: {
            name: 'file_id',
            referencedColumnName: 'id'
        }
    })
    files: FileProperty[]
}