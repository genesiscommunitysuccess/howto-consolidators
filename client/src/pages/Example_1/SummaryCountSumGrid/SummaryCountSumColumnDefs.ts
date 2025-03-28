import { ColDef } from '@ag-grid-community/core';
import { getNumberFormatter } from '@genesislcap/foundation-utils';

export const columnDefs: ColDef[] = [
  {
    field: "BUY_COUNT",
  },
  {
    field: "SELL_COUNT",
  },
    {
      field: "BUY_SUM",
      valueFormatter: getNumberFormatter("0,0.00", null),
    },
    {
      field: "SELL_SUM",
      valueFormatter: getNumberFormatter("0,0.00", null),
    },
  {
    field: "INSTRUMENT_CODE",
  }
]
