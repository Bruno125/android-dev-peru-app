package dev.android.peru.data.source.mock.repositories

import dev.android.peru.data.repositories.MeetupsRepo
import peru.android.dev.datamodel.Meetup

class MockMeetupsRepo : MeetupsRepo {

    override suspend fun getMeetups(): List<Meetup> {
        return listOf(
                Meetup(id = "w1g6dl3kfal",
                        name = "Demo Meetup #1",
                        description = "Test!",
                        questionnaire = MockQuestionnaireRepo().getQuestionnaireForMeetup("w1g6dl3kfal")
                ),
                Meetup(id = "b1sfdjl342sfa",
                        name = "Demo Meetup #2",
                        description = "Test 2!",
                        questionnaire = MockQuestionnaireRepo().getQuestionnaireForMeetup("b1sfdjl342sfa")
                )
        )
    }

    override suspend fun getMeetup(meetupId: String): Meetup {
        return getMeetups().first()
    }

}
