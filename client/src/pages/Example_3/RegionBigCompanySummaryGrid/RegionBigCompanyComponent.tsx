import { columnDefs as columnDefsTile } from './RegionBigCompanyColumnDefs.ts'
import './RegionBigCompanyColumn.css';


export const Example_3SummaryRegionBigCompanyView: React.FC = () => {
    const columnDefs: typeof columnDefsTile = columnDefsTile

    return (
        <entity-management
            design-system-prefix='rapid'
            enable-row-flashing
            enable-cell-flashing
            resourceName="ALL_REGION_BIG_COMPANY_SUMMARY"
            columns={columnDefs}
            modal-position='centre'
            size-columns-to-fit
        ></entity-management>
    );
};