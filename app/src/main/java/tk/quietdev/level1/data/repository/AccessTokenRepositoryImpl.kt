package tk.quietdev.level1.data.repository

import tk.quietdev.level1.data.db.RoomUserDao

class AccessTokenRepositoryImpl(
    private val db: RoomUserDao,
) {
    private suspend fun getCurrentUserToken(): String {
        return db.getCurrentUser().accessToken
    }
}