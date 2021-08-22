import { FileProperty } from "src/file/file.entity";
import { Note } from "src/note/note.entity";
import { Column, Entity, ManyToOne, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { v4 as uuidv4 } from 'uuid';
import { Exclude, Expose } from "class-transformer";


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

    @Column({unique: true})
    @Exclude()
    token: string;

    @Column()
    @Exclude()
    encryptedPassword: string;

    @OneToMany(type => Note, post => post.account)
    notes: Note[]

    @OneToMany(type => FileProperty, file => file.account)
    files: FileProperty[]

    @ManyToOne(type => FileProperty)
    avatarIcon: FileProperty;

    constructor(username: string) {
        this.username = username;
    }

    applyPassword(rawPassword: string): void {
        this.encryptedPassword = rawPassword;
        //const saltRounds: number = 10;
        /*const salt: string = bcrypt.genSaltSync(saltRounds);
        this.encryptedPassword = bcrypt.hashSync(rawPassword, salt);*/
    }

    async checkPassword(rawPassword: string): Promise<boolean> {
        //return bcrypt.compare(rawPassword, this.encryptedPassword);
        return this.encryptedPassword == rawPassword;
    }

    generateToken(force: boolean = false) : boolean {
        if(force || (this.token == null || this.token.length == 0)) {
            this.token = uuidv4();
            return true;
        }
        return false;
    }

}