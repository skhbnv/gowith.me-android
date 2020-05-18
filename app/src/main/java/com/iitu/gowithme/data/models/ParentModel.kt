package com.iitu.gowithme.data.models

import com.iitu.gowithme.responses.GeneralEvents

data class ParentModel (
    var title: String = " ",
    var children: List<GeneralEvents>,
    var layoutType: Int
)