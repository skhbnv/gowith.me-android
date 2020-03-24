package com.example.gowithme.models

import com.example.gowithme.responses.GeneralEvents

data class ParentModel (
    var title: String = " ",
    var children: List<GeneralEvents>
)