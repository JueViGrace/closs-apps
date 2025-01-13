package org.closs.core.types.data

import org.closs.core.database.helper.DbHelper

interface LocalSource {
    val dbHelper: DbHelper
}