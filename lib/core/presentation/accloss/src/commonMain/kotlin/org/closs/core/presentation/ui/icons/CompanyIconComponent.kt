package org.closs.core.presentation.ui.icons

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.company_logo
import org.closs.core.types.common.selectCompanyImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CompanyIconComponent(
    modifier: Modifier = Modifier.sizeIn(minWidth = 24.dp, minHeight = 24.dp, maxWidth = 26.dp, maxHeight = 26.dp),
    company: String,
) {
    IconComponent(
        modifier = modifier,
        painter = painterResource(selectCompanyImage(company)),
        contentDescription = stringResource(Res.string.company_logo)
    )
}
