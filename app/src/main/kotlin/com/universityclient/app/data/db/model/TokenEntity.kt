package com.universityclient.app.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.universityclient.app.data.db.constant.TableNames
import com.universityclient.domain.Token

@Entity(
    tableName = TableNames.TokenTable
)
data class TokenEntity(
    @PrimaryKey val token: String
) {

    fun transform() = Token(
        token = token
    )
}
