package tk.quietdev.level1.ui.pager.contacts.list.adapter

interface ItemStateChecker {
    fun isItemSelected(id: Int): Boolean = false
    fun isItemAdded(id: Int): Boolean = false
}