package com.itv.scalapact.argonaut62

import argonaut.Argonaut._
import argonaut._
import com.itv.scalapact.shared.{Pact, PactMetaData, PactsForVerificationRequest, VersionMetaData}
import com.itv.scalapact.shared.json.IPactWriter

class PactWriter extends IPactWriter {
  import PactImplicits._

  def pactToJsonString(pact: Pact, scalaPactVersion: String): String = {
    val updatedMetaData: Option[PactMetaData] =
      pact.metadata.orElse {
        Option(
          PactMetaData(
            pactSpecification = Option(VersionMetaData("2.0.0")), //TODO: Where to get this value from?
            `scala-pact` = Option(VersionMetaData(scalaPactVersion))
          )
        )
      }

    pact.copy(metadata = updatedMetaData).asJson.pretty(PrettyParams.spaces2.copy(dropNullKeys = true))
  }

  def pactsForVerificationRequestToJsonString(request: PactsForVerificationRequest): String =
    request.asJson.pretty(PrettyParams.spaces2.copy(dropNullKeys = true))
}
