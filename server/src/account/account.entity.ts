import { FileProperty } from "src/file/file.entity";
import { Note } from "src/note/note.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import bcrypt from 'bcrypt';

@Entity({
    name: 'accounts'
})
export class Account {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({
        unique: true
    })
    username: string;

    @Column()
    encryptedPassword: string;

    @OneToMany(type => Note, post => post.account)
    notes: Note[]

    @OneToMany(type => FileProperty, file => file.account)
    files: FileProperty[]

    constructor(username: string) {
        this.username = username;
    }

    applyPassword(rawPassword: string): void {
        const saltRounds: number = 10;
        const salt: string = bcrypt.genSaltSync(saltRounds);
        this.encryptedPassword = bcrypt.hashSync(rawPassword, salt);
    }

    checkPassword(rawPassword: string): Promise<boolean> {
        return bcrypt.compare(rawPassword, this.encryptedPassword);
    }

}