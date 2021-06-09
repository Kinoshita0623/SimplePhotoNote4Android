package ie2a_2200078.eventwork05.view

import ie2a_2200078.eventwork05.entities.GalleryFile

interface FileListener {

    fun onSelect(file: GalleryFile)

    fun onDetach(file: GalleryFile)
}