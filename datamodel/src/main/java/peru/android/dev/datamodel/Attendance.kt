package peru.android.dev.datamodel

data class Attendance(val id: String,
                      val name: String,
                      var hasVoted: Boolean,
                      var didShowUp: Boolean)