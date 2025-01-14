package org.closs.core.types.aliases

import org.closs.core.database.Closs_order
import org.closs.core.database.Closs_order_lines
import org.closs.core.database.FindOrderByUserWithLines

typealias DbOrder = Closs_order
typealias DbOrderByUserWithLines = FindOrderByUserWithLines
typealias DbOrderLines = Closs_order_lines
