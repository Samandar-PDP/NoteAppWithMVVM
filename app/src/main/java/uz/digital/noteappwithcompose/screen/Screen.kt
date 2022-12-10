package uz.digital.noteappwithcompose.screen

sealed class Screen(
    val route: String
) {
    object Home: Screen(
        route = "home_screen"
    )
    object Detail: Screen(
        route = "add_update_screen"
    )
}
