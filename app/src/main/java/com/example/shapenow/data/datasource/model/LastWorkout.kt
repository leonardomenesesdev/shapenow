import com.google.firebase.Timestamp

data class LastWorkout(
    val workoutId: String = "",
    val completedAt: Timestamp = Timestamp.now()
)