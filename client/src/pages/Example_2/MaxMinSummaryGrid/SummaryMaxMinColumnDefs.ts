import { ColDef } from '@ag-grid-community/core';
import { getNumberFormatter } from '@genesislcap/foundation-utils';

export const columnDefs: ColDef[] = [
  {
    field: "BUY_MAX",
    valueFormatter: getNumberFormatter("0,0.00", null),

  },
  {
    field: "SELL_MAX",
    valueFormatter: getNumberFormatter("0,0.00", null),

  },
    {
      field: "BUY_MIN",
      valueFormatter: getNumberFormatter("0,0.00", null),

    },
    {
      field: "SELL_MIN",
      valueFormatter: getNumberFormatter("0,0.00", null),

    },
  {
    field: "INSTRUMENT_CODE",
  }
]
