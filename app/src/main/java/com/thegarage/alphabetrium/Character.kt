package com.thegarage.alphabetrium

data class Location(val name: String, val url: String)

data class Character(val id: Int, val name: String, val status: String, val species: String,
                     val type: String, val gender: String, val location: Location)

data class Info(val count: Int, val pages: Int, val prev: String?, val next: String?)

data class RMRequest(val info: Info, val characters: List<Character>)