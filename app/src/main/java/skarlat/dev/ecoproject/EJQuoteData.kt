package skarlat.dev.ecoproject

import work.upstarts.editorjskit.models.data.EJData

data class EJQuoteData(
        val text: String
): EJData()

data class EJAdviceLinkData(
        val text: String,
        val url: String
): EJData()