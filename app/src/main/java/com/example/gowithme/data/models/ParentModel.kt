package com.example.gowithme.data.models

import com.example.gowithme.responses.GeneralEvents

data class ParentModel (
    var title: String = " ",
    var children: List<GeneralEvents>,
    var layoutType: Int
)