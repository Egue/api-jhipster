package com.hjsolutions.isp_api.web.rest

data class DataResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null,
    val error: String? = null
) {
    companion object {
        fun <T> success(data: T): DataResponse<T> {
            return DataResponse(
                status = "success",
                message = "Request was successful",
                data = data
            )
        }

        fun error(message: String, error: String? = null): DataResponse<Nothing> {
            return DataResponse(
                status = "error",
                message = message,
                error = error
            )
        }
    }
}