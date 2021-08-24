package ie2a_2200078.eventwork05.domain

import ie2a_2200078.eventwork05.client.Account as AccountDTO
import ie2a_2200078.eventwork05.client.Note as NoteDTO
import ie2a_2200078.eventwork05.client.FileProperty as FileDTO

fun AccountDTO.toAccount() : Account{
    return Account(
        this.id,
        this.username,
        this.avatarIcon?.toFileProperty()
    )

}

fun FileDTO.toFileProperty(): FileProperty {
    return FileProperty(
        this.id,
        name = this.name,
        originalName = this.originalName,
        type = this.type,
    )
}

fun NoteDTO.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        description = this.description,
        isPrivate = this.isPrivate,
        account = this.account.toAccount(),
        accountId = this.accountId,
        files = this.files.map {
            it.toFileProperty()
        }
    )
}