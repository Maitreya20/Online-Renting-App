package com.college.model

import com.google.gson.annotations.SerializedName

data class MyBookingDetails(
    @SerializedName("or_id"         ) var orId         : String? = null,
    @SerializedName("u_id"          ) var uId          : String? = null,
    @SerializedName("p_id"          ) var pId          : String? = null,
    @SerializedName("or_amount"     ) var orAmount     : String? = null,
    @SerializedName("or_card_no"    ) var orCardNo     : String? = null,
    @SerializedName("or_card_month" ) var orCardMonth  : String? = null,
    @SerializedName("or_card_year"  ) var orCardYear   : String? = null,
    @SerializedName("or_card_cvv"   ) var orCardCvv    : String? = null,
    @SerializedName("or_card_name"  ) var orCardName   : String? = null,
    @SerializedName("or_time"       ) var orTime       : String? = null,
    @SerializedName("u_name"        ) var uName        : String? = null,
    @SerializedName("u_email"       ) var uEmail       : String? = null,
    @SerializedName("u_phone"       ) var uPhone       : String? = null,
    @SerializedName("u_address"     ) var uAddress     : String? = null,
    @SerializedName("p_type"        ) var pType        : String? = null,
    @SerializedName("p_photo"       ) var pPhoto       : String? = null,
    @SerializedName("p_name"        ) var pName        : String? = null,
    @SerializedName("p_description" ) var pDescription : String? = null,
    @SerializedName("p_rent"        ) var pRent        : String? = null,
    @SerializedName("p_status"      ) var pStatus      : String? = null
)