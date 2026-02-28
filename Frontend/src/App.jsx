import React from 'react';
import ProductList from './components/Product/ProductList';
import RawMaterialList from './components/RawMaterial/RawMaterialList';
import ProductProductionList from './components/Product/ProductProductionList';

function App() {
  return (
    <div className="App">
      <h1>Inventory System</h1>
      <section>
        <ProductList />
      </section>

      <section>
        <RawMaterialList />
      </section>

      <section>
        <ProductProductionList />
      </section>
    </div>
  );
}
export default App;