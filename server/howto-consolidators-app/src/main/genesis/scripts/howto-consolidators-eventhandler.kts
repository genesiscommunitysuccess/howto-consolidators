eventHandler {
  eventHandler<Trade>("TRADE_INSERT", transactional = true) {
    onCommit { event ->
      val details = event.details
      val insertedRow = entityDb.insert(details)
      // return an ack response which contains a list of record IDs
      ack(listOf(mapOf(
        "TRADE_ID" to insertedRow.record.tradeId,
      )))
    }
  }
  eventHandler<Trade>("TRADE_MODIFY", transactional = true) {
    onCommit { event ->
      val details = event.details
      entityDb.modify(details)
      ack()
    }
  }
  eventHandler<Trade.ById>("TRADE_DELETE", transactional = true) {
    onCommit { event ->
      val details = event.details
      entityDb.delete(details)
      ack()
    }
  }

}
