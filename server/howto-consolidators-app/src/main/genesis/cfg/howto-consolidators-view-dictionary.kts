views {
  view("TRADE_VIEW", TRADE) {
    joins {
      joining(INSTRUMENT) {
        on( TRADE { INSTRUMENT_CODE } to INSTRUMENT { ISIN })
      }
    }
    fields {
      TRADE.allFields()

      INSTRUMENT.TICKER
      INSTRUMENT.REGION
    }
  }
}
