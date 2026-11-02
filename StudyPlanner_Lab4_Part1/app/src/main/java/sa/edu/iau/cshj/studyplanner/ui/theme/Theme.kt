package sa.edu.iau.cshj.studyplanner.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val StudyPlannerColors = lightColorScheme()

@Composable
fun StudyPlannerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = StudyPlannerColors, content = content)
}
