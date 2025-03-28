import { ConnectedRenderersOptions, UiSchema } from '@genesislcap/foundation-forms';

const selectData = ['NAIN', 'EUIN', 'APACIN', 'LATAMIN', 'MEAIN']

export const createFormSchema: UiSchema = {
  "type": "VerticalLayout",
  "elements": [
    {
      "type": "Control",
      "label": "Trade Date",
      "scope": "#/properties/TRADE_DATE",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Trade Type",
      "scope": "#/properties/TRADE_SIDE",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Trade Status",
      "scope": "#/properties/TRADE_STATUS",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Instrument Code",
      "scope": "#/properties/INSTRUMENT_CODE",
      "options": <ConnectedRenderersOptions>{
        data: selectData.map((label) => ({ label })),
        labelField: "label",
        valueField: "label",
      },
    },
    {
      "type": "Control",
      "label": "Price",
      "scope": "#/properties/PRICE",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Quantity",
      "scope": "#/properties/QUANTITY",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Trade Id",
      "scope": "#/properties/TRADE_ID",
      "options": {
        "hidden": true
      }
    },
  ]
}
