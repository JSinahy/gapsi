package com.laraguzman.gapsiecommerce.data.models

class GeneralResponse<T> (
        var totalResults: Int,
        var page: String,
        var items: T
)

