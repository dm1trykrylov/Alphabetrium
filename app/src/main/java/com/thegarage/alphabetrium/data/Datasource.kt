package com.thegarage.alphabetrium.data

import com.thegarage.alphabetrium.R
import com.thegarage.alphabetrium.model.Characters


class Datasource {

    fun loadAffirmations(): List<Characters> {
        return listOf<Characters>(
            Characters(1),
            Characters(2),
            Characters(3)
        )
    }
}