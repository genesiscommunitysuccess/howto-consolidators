import { createFormSchema as createFormSchemaTile } from './TradesCreateFormSchema';
import { updateFormSchema as updateFormSchemaTile } from './TradesUpdateFormSchema';
import { columnDefs as columnDefsTile } from './TradesColumnDefs';
import './TradesComponent.css';


export const TradesManager: React.FC = () => {
const createFormSchema: typeof createFormSchemaTile = createFormSchemaTile;
const updateFormSchema: typeof updateFormSchemaTile = updateFormSchemaTile;
const columnDefs: typeof columnDefsTile = columnDefsTile;

  return (
    <entity-management
      design-system-prefix="rapid"
      enable-row-flashing
      enable-cell-flashing
      resourceName="ALL_TRADES"
    createEvent="EVENT_TRADE_INSERT"
    createFormUiSchema={createFormSchema}
    updateEvent="EVENT_TRADE_MODIFY"
    updateFormUiSchema={updateFormSchema}
    deleteEvent="EVENT_TRADE_DELETE"
    columns={columnDefs}
    modal-position="centre"
    size-columns-to-fit
    enable-search-bar
    ></entity-management>
  );
};