package org.closs.core.types.common

import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_avlogo
import org.closs.core.resources.resources.generated.resources.ic_wokin_logo
import org.closs.core.types.company.Companies
import org.jetbrains.compose.resources.DrawableResource

fun selectCompanyImage(company: String): DrawableResource {
    return when (company) {
        Companies.CLO.code -> Res.drawable.ic_avlogo
        Companies.WOKIN.code -> Res.drawable.ic_wokin_logo
        else -> Res.drawable.ic_avlogo
    }
}
