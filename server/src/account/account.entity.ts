import { FileProperty } from "src/file/file.entity";
import { Post } from "src/post/post.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

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

    @OneToMany(type => Post, post => post.account)
    posts: Post[]

    @OneToMany(type => FileProperty, file => file.account)
    files: FileProperty[]

}