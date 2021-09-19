package tk.quietdev.level1.ui.pager.contacts.list.adapter

interface HolderStateChecker {
    fun isItemSelected(id: Int): Boolean = false
}

enum class HolderState {
    ADDED, PENDING, FAIL
}