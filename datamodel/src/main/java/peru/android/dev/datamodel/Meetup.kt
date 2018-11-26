package peru.android.dev.datamodel

data class Meetup @JvmOverloads constructor(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val cover: String = "",
        val location: MeetupLocation? = null,
        val questionnaire: Questionnaire? = null
)