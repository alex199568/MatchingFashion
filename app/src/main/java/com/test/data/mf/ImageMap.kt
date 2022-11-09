package com.test.data.mf

import com.test.data.mf.ApiImage

data class ImageMap(
    val thumbnail: ApiImage,
    val medium: ApiImage,
    val large: ApiImage
) {
}