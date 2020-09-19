package skarlat.dev.ecoproject

import work.upstarts.editorjskit.models.EJAbstractBlockType

enum class EJQuoteBlockType(override val jsonName: String) : EJAbstractBlockType {
    QUOTE("quote")
}