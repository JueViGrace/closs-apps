package org.closs.core.api.company

import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.types.company.dto.CompanyDto

class CompanyClient(
    private val client: KtorClient
) {
    suspend fun getCompanyByCode(code: String): ApiOperation<CompanyDto> {
        return client.call {
            get(
                urlString = "/api/company/$code"
            )
        }
    }
}
