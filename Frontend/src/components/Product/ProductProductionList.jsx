import React, { useEffect, useState } from "react";
import axios from "axios";

const ProductProductionList = () => {
  const [products, setProducts] = useState([]);

  const fetchProduction = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/productions/suggestions"
      );
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching production list:", error);
    }
  };

  useEffect(() => {
    fetchProduction();
  }, []);

  return (
    <div>
      <h2>Products Possible to Produce</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Possible Quantity</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <tr key={p.productId}>
              <td>{p.productName}</td>
              <td>{p.quantityPossible}</td>
              <td>{p.totalValue}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductProductionList;