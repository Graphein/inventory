import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css/ProductProductionList.css";

const API_PRODUCTIONS = process.env.REACT_APP_API_PRODUCTIONS;

const ProductProductionList = () => {
  const [products, setProducts] = useState([]);

  const fetchProduction = async () => {
    try {
      const response = await axios.get(API_PRODUCTIONS);
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching production list:", error);
    }
  };


  useEffect(() => {
    fetchProduction();
  }, []);

  return (
    <div className="production-container">
      <h2 className="production-title">Products Possible to Produce</h2>

      <div className="card">
        <div className="table-wrapper">
          <table className="production-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Possible Quantity</th>
                <th>Total Value ($)</th>
              </tr>
            </thead>
            <tbody>
              {products.length > 0 ? (
                products.map((p) => (
                  <tr key={p.productId}>
                    <td>{p.productName}</td>
                    <td>
                      <span
                        className={`badge ${
                          p.quantityPossible > 0
                            ? "badge-success"
                            : "badge-danger"
                        }`}
                      >
                        {p.quantityPossible}
                      </span>
                    </td>
                    <td>${p.totalValue?.toFixed(2)}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3" className="no-data">
                    No products available for production
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ProductProductionList;