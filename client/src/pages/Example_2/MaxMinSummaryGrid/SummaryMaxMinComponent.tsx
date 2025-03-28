import { columnDefs as columnDefsTile } from './SummaryMaxMinColumnDefs'
import './SummaryMaxMinColumn.css';


export const Example_2SummaryMaxMinView: React.FC = () => {
    const columnDefs: typeof columnDefsTile = columnDefsTile

    return (
        <entity-management
            design-system-prefix='rapid'
            enable-row-flashing
            enable-cell-flashing
            resourceName="ALL_MAX_MIN_SUMMARY"
            columns={columnDefs}
            modal-position='centre'
            size-columns-to-fit
        ></entity-management>
    );
};