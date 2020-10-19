package org.akvo.flow.mock.util

import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("dataPointId") val dataPointId : String,
	@SerializedName("deviceId") val deviceId : String,
	@SerializedName("uuid") var uuid : String,
	@SerializedName("formId") val formId : Int,
	@SerializedName("username") val username : String,
	@SerializedName("responses") val responses : List<Responses>,
	@SerializedName("submissionDate") val submissionDate : Long,
	@SerializedName("formVersion") val formVersion : Int,
	@SerializedName("duration") val duration : Int
)

data class Responses (

	@SerializedName("answerType") val answerType : String,
	@SerializedName("iteration") val iteration : Int,
	@SerializedName("questionId") val questionId : Int,
	@SerializedName("value") var value : String
)