import java.math.BigDecimal

tables {
  table(name = "TRADE", id = 11_000) {
    field("TRADE_ID", STRING).sequence("TI")
    field("PRICE", BIGDECIMAL).notNull()
    field("QUANTITY", BIGDECIMAL).notNull()
    field("TRADE_DATE", DATE).notNull()
    field("TRADE_SIDE", ENUM("SELL","BUY")).default("BUY").notNull()
    field("TRADE_STATUS", ENUM("VERIFIED", "CANCELLED")).notNull()
    field("INSTRUMENT_CODE", STRING).notNull().nonUniqueIndex()

    primaryKey("TRADE_ID")
  }

  table(name = "COUNT_SUM_SUMMARY", id = 11_003) {
    field("INSTRUMENT_CODE", STRING)
    field("BUY_SUM", BIGDECIMAL).default(BigDecimal.ZERO)
    field("SELL_SUM", BIGDECIMAL).default(BigDecimal.ZERO)
    field("BUY_COUNT", INT).default(0)
    field("SELL_COUNT", INT).default(0)

    primaryKey("INSTRUMENT_CODE")
  }

  table(name = "MAX_MIN_SUMMARY", id = 11_004) {
    field("INSTRUMENT_CODE", STRING)
    field("BUY_MIN", BIGDECIMAL)
    field("SELL_MIN", BIGDECIMAL)
    field("BUY_MAX", BIGDECIMAL)
    field("SELL_MAX", BIGDECIMAL)

    primaryKey("INSTRUMENT_CODE")
  }

  table(name = "INSTRUMENT", id = 11_006) {
    field("ISIN", STRING).sequence("IN")
    field("REGION", ENUM("NA","EU","APAC","LATAM","MEA")).notNull()
    field("COMPANY", STRING).notNull()
    field("NAME", STRING).notNull()
    field("TICKER", STRING).notNull()

    primaryKey("ISIN")
  }

  table(name = "REGION_BIG_COMPANY_SUMMARY", id = 11_007) {
    field("DATE", DATE)
    field("NA_INCOMPLETE_COUNT", INT)
    field("NA_COMPLETE", BOOLEAN).default(true)
    field("EU_INCOMPLETE_COUNT", INT)
    field("EU_COMPLETE", BOOLEAN).default(true)
    field("APAC_INCOMPLETE_COUNT", INT)
    field("APAC_COMPLETE", BOOLEAN).default(true)
    field("LATAM_INCOMPLETE_COUNT", INT)
    field("LATAM_COMPLETE", BOOLEAN).default(true)
    field("MEA_INCOMPLETE_COUNT", INT)
    field("MEA_COMPLETE", BOOLEAN).default(true)
    field("BIG_COMPANY_TRADED_COUNT", INT)
    field("BIG_COMPANY_TRADED", BOOLEAN).default(false)

    primaryKey("DATE")
  }
}
