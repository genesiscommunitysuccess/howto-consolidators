import { columnDefs as columnDefsTile } from './SummaryCountSumColumnDefs';
import './SummaryCountSumComponent.css';


export const Example_1SummaryCountSumView: React.FC = () => {
const columnDefs: typeof columnDefsTile = columnDefsTile;

  return (
    <entity-management
      design-system-prefix="rapid"
      enable-row-flashing
      enable-cell-flashing
      resourceName="ALL_SUMMARY"
    columns={columnDefs}
    modal-position="centre"
    size-columns-to-fit
    ></entity-management>
  );
};