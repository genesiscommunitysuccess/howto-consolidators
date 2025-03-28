import './TradePage.css';
import { persistLayout } from '../../utils';
import { TradesManager } from './TradesManager';

const TradePage = () => {
  return (
    <section className="Trade-page">
      <rapid-layout auto-save-key={persistLayout('Trade')}>
        <rapid-layout-region>
          <rapid-layout-item title="TRADES">
            <TradesManager></TradesManager>
          </rapid-layout-item>
        </rapid-layout-region>
      </rapid-layout>
    </section>
  );
};

export default TradePage;
