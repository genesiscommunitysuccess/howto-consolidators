import './Example_2.css';
import { persistLayout } from '../../utils';
import { Example_2SummaryMaxMinView } from './MaxMinSummaryGrid'

const Example_2 = () => {
  return (
    <section className="example-2-page">
      <rapid-layout auto-save-key={persistLayout('Example 2')}>
        <rapid-layout-region>
          <rapid-layout-item title="SUMMARY">
            <Example_2SummaryMaxMinView></Example_2SummaryMaxMinView>
          </rapid-layout-item>
        </rapid-layout-region>
      </rapid-layout>
    </section>
  );
};

export default Example_2;
