package com.dev.frequenc.ui_codes.data

import java.io.Serializable

data class AudienceDataResponse (
val _id: String,
val status: String,
val mobile_no: String,
val tickets: List<Any>,
val genreType: List<String>,
val profileType: String,
val created_at: String,
val updated_at: String,
val __v: Int,
var banner_image: String,
val city: String,
val country: String,
val description: String,
val dob: String,
val email: String,
val fullName: String,
val gender: String,
val name: String,
val postalCode: String,
var profile_pic: String,
val state: String,
val id: String,
val vibes :  String,
val height : String,
val enjoyType: List<Any>,
val vibesDate: String,
val drinking: String,
val education: String,
val language: List<String>,
val location: String,
val occupation: String,
val pets: String,
val profile_images: List<String>,
val religion: String,
val smoking: String,
val zodiac_sign: String,
val children : String
) : Serializable
