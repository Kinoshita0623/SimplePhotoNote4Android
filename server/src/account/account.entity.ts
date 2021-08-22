import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

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
    
}