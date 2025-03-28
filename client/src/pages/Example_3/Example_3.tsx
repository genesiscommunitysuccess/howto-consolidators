import './Example_3.css';
import { persistLayout } from '../../utils';
import { Example_3SummaryRegionBigCompanyView } from './RegionBigCompanySummaryGrid'

const Example_3 = () => {
  return (
    <section className="example-3-page">
      <rapid-layout auto-save-key={persistLayout('Example 3')}>
        <rapid-layout-region>
          <rapid-layout-item title="SUMMARY">
            <Example_3SummaryRegionBigCompanyView></Example_3SummaryRegionBigCompanyView>
          </rapid-layout-item>
        </rapid-layout-region>
      </rapid-layout>
    </section>
  );
};

export default Example_3;
