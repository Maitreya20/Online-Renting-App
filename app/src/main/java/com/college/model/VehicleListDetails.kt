package com.college.model

import com.google.gson.annotations.SerializedName

data class VehicleListDetails(
    @SerializedName("p_id"          ) var pId          : String? = null,
    @SerializedName("o_id"          ) var oId          : String? = null,
    @SerializedName("p_type"        ) var pType        : String? = null,
    @SerializedName("p_photo"       ) var pPhoto       : String? = null,
    @SerializedName("p_name"        ) var pName        : String? = null,
    @SerializedName("p_description" ) var pDescription : String? = null,
    @SerializedName("p_rent"        ) var pRent        : String? = null,
    @SerializedName("p_status"      ) var pStatus      : String? = null,
    @SerializedName("p_time"        ) var pTime        : String? = null,
    @SerializedName("o_name"        ) var oName        : String? = null,
    @SerializedName("o_email"       ) var oEmail       : String? = null,
    @SerializedName("o_phone"       ) var oPhone       : String? = null,
    @SerializedName("o_address"     ) var oAddress     : String? = null
)
