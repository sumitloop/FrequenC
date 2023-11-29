package com.dev.frequenc.ui_codes.data

data class VibeEventResponse(
    val `data`: List<VibeResponse>,
    val message: String,
    val status: Boolean
)

class Venueid(
    val __v: Int,
    val _id: String,
    val address: String,
    val bank_id: List<Any>,
    val banner_pic: String,
    val basic_amenities: List<String>,
    val blackout_dates: List<Any>,
    val business_rules: List<Any>,
    val city: String,
    val country: String,
    val created_at: String,
    val description: String,
    val email: String,
    val event: List<Any>,
    val facebook_url: String,
    val fullName: String,
    val gallery: List<String>,
    val id: String,
    val instagram_url: String,
    val managed_by: String,
    val manager: List<Any>,
    val maximum_capacity: Int,
    val minimum_capacity: Int,
    val phone_no: String,
    val portfolio: List<Any>,
    val postalCode: String,
    val profile_pic: String,
    val section: List<String>,
    val section_details: List<Any>,
    val state: String,
    val status: String,
    val tags: List<Any>,
    val twitter_url: String,
    val updated_at: String,
    val venue_locality: String,
    val venue_name: String,
    val venue_price: Int,
    val venue_type: List<String>,
    val youtube_url: String
)

data class VibeResponse(
    val __v: Int,
    val _id: String,
    val artist_id: List<String>,
    val audience_id: List<Any>,
    val bceventid: String,
    val category: String,
    val city: String,
    val country: String,
    val created_at: String,
    val description: String,
    val eventCapacity: String,
    val eventEndDate: String,
    val eventEndTime: String,
    val eventImage: List<String>,
    val eventStartDate: String,
    val eventStartTime: String,
    val eventTitle: String,
    val event_created_by: String,
    val event_publish_type: String,
    val gallery: List<Any>,
    val is_published: Boolean,
    val language: String,
    val state: String,
    val status: String,
    val sub_category: List<String>,
    val tags: List<Any>,
    val tickets: List<Any>,
    val trendingScore: Int,
    val updated_at: String,
    val user_id: String,
    val vendor_id: List<Any>,
    val venueApproval: Boolean,
    val venueid: Venueid
)