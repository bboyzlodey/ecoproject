package skarlat.dev.ecoproject

import work.upstarts.editorjskit.models.EJAbstractBlockType

/**
 * Enum class contains custom EJ blocks. Declare it this.
 */
enum class ArticleEcoTipsBlocks(override val jsonName: String) : EJAbstractBlockType {
    QUOTE("quote"),
    ADVICE_LINK("advice_link"),
    ARTICLE_IMAGE("article_image");

    companion object {
        fun fromString(jsonName: String): ArticleEcoTipsBlocks {
            return when (jsonName){
                "quote" -> QUOTE
                "advice_link" -> ADVICE_LINK
                "article_image" -> ARTICLE_IMAGE
                else -> throw IllegalArgumentException(jsonName)
            }
        }
    }
}