import { ColDef } from '@ag-grid-community/core';
import { getNumberFormatter, getDateFormatter } from '@genesislcap/foundation-utils';

export const columnDefs: ColDef[] = [
  {
    field: "TRADE_DATE",
  },
  {
    field: "TRADE_SIDE",
  },
  {
    field: "TRADE_STATUS",
  },
  {
    field: "PRICE",
    valueFormatter: getNumberFormatter("0,0.00", null),
  },
  {
    field: "QUANTITY",
    valueFormatter: getNumberFormatter("0,0.00", null),
  },
  {
    field: "TRADE_ID",
  },
  {
    field: "INSTRUMENT_CODE",
  }
]
