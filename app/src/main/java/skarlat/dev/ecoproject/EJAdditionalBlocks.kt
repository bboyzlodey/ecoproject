package skarlat.dev.ecoproject

import work.upstarts.editorjskit.models.EJAbstractBlockType
import work.upstarts.editorjskit.models.EJBlockType

/**
 * Enum class contains custom EJ blocks. Declare it this.
 */
enum class EJAdditionalBlocks(override val jsonName: String) : EJAbstractBlockType {
    QUOTE("quote"),
    ADVICE_LINK("advice_link");

    companion object {
        fun fromString(jsonName: String): EJAdditionalBlocks {
            return when (jsonName){
                "quote" -> QUOTE
                "advice_link" -> ADVICE_LINK
                else -> throw IllegalArgumentException(jsonName)
            }
        }
    }
}