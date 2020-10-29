package skarlat.dev.ecoproject

import work.upstarts.editorjskit.models.data.EJData

data class ArticleQuoteData(
        val text: String
): EJData()

data class ArticleAdviceLinkData(
        val text: String,
        val url: String
): EJData()

data class ArticleImageData(
        val caption: String,
        val path: String
): EJData()