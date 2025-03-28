import './Example_1.css';
import { persistLayout } from '../../utils';
import { Example_1SummaryCountSumView } from './SummaryCountSumGrid';

const Example_1 = () => {
  return (
    <section className="example-1-page">
      <rapid-layout auto-save-key={persistLayout('Example 1_1734101814548')}>
        <rapid-layout-region>
          <rapid-layout-item title="SUMMARY">
            <Example_1SummaryCountSumView></Example_1SummaryCountSumView>
          </rapid-layout-item>
        </rapid-layout-region>
      </rapid-layout>
    </section>
  );
};

export default Example_1;
