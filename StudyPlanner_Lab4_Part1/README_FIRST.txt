CSC 402 Lab 4 — Semester App, Part 1: StudyPlanner

Project settings
- Project: StudyPlanner
- Package: sa.edu.iau.cshj.studyplanner
- Kotlin + Jetpack Compose, minimum SDK 26
- Material 3 only
- Exactly one Activity, with MAIN and LAUNCHER

Included
- PlannerUiState immutable data class with defaults
- Stateless PlannerHomeScreen with a lambda and modifier last
- Two previews with different progress states
- PlannerHomeRoute owns state and updates it using copy()
- MainActivity calls PlannerHomeRoute from setContent
- Paper sketch and state-list reference PDF
- AI use disclosure log

Before submitting
1. Open this folder in Android Studio and allow Gradle Sync.
2. Run on an emulator/device and tap “Mark one task done”.
3. Open PlannerHomeScreen.kt in Design/Split view and capture both real previews.
4. Take a photo of your own hand-drawn paper sketch and state list if your instructor requires a paper photo. The included PDF is a reference, not a hand-drawn photo.
5. Update the AI log to accurately describe your review and edits.
6. Submit the ZIP, paper sketch photo, two preview screenshots, and running-app screenshot as required by Blackboard.

Note: This project has not been built or run in Android Studio in this environment, so please verify it before submission. No fake screenshots are included.
