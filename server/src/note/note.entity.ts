import { Account } from "src/account/account.entity";
import { FileProperty } from "src/file/file.entity";
import { Column, Entity, JoinTable, ManyToMany, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity({
    name: 'notes'
})
export class Note {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    text: string;

    @Column()
    author: number;

    @Column()
    accountId: number;

    @ManyToOne(type => Account, account => account.notes)
    account: Account;

    @Column({
        default: false
    })
    isPrivate: boolean = false;

    @Column({
        default: 0
    })
    favoriteCount: number = 0;

    @ManyToMany(type => FileProperty)
    @JoinTable({
        name: 'note_files',
        joinColumn: {
            name: 'note_id',
            referencedColumnName: 'id'
        },
        inverseJoinColumn: {
            name: 'file_id',
            referencedColumnName: 'id'
        }
    })
    files: FileProperty[]
}