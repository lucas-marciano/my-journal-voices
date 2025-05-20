package com.cafecomandroid.myjournalvoice.core.presentation.ui.dropdown

data class Selectable<T>(
    val item: T,
    val selected: Boolean = false
) {
    companion object {
        fun <T> List<T>.asUnselectedItems(): List<Selectable<T>> {
            return map {
                Selectable(
                    item = it,
                    selected = false
                )
            }
        }
    }
}
