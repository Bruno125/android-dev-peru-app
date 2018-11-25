package peru.android.dev.datamodel

class Meetup @JvmOverloads constructor(
        val name: String = "",
        val description: String = "",
        val location: MeetupLocation? = null,
        val questionnaire: Questionnaire? = null
)