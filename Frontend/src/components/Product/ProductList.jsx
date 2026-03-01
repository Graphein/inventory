import React, { useState, useEffect } from "react";
import {
  getAllProducts,
  deleteProduct,
  createProduct,
  updateProduct,
} from "../../api/productApi";
import ProductForm from "./ProductForm";
import "../css/ProductList.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [editingProduct, setEditingProduct] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const fetchProducts = () => {
    getAllProducts().then((res) => setProducts(res.data));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => {
        setErrorMessage("");
      }, 3000);

      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  const handleDelete = (id) => {
    deleteProduct(id)
    .then(() => {
      fetchProducts();
      setErrorMessage("");
    })
    .catch((error) => {
      if (error.response?.status === 409) {
        setErrorMessage(
          "Cannot delete product because it has linked raw materials."
        );
      } else {
        setErrorMessage("Unexpected error while deleting product.");
      }
    });
  };

  const handleSave = (product) => {
    const request = product.id
      ? updateProduct(product.id, product)
      : createProduct(product);

    request.then(() => {
      fetchProducts();
      setShowForm(false);
      setEditingProduct(null);
    });
  };

  return (
    <div className="product-container">
      <h2 className="product-title">Products</h2>

      <button
        className="create-button"
        onClick={() => {
          setEditingProduct(null);
          setShowForm(true);
        }}
      >
        + Create Product
      </button>
      {errorMessage && (
      <div className="error-message">
        {errorMessage}
      </div>
      )}
      {showForm && (
        <ProductForm
          product={editingProduct}
          onSave={handleSave}
          onClose={() => {
            setShowForm(false);
            setEditingProduct(null);
          }}
        />
      )}

      <div className="table-wrapper">
        <table className="product-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Price ($)</th>
              <th>Quantity</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {products.length > 0 ? (
              products.map((p) => (
                <tr key={p.id}>
                  <td>{p.name}</td>
                  <td>{p.price?.toFixed(2)}</td>
                  <td>{p.quantity}</td>
                  <td className="actions">
                    <button
                      className="edit-btn"
                      onClick={() => {
                        setEditingProduct(p);
                        setShowForm(true);
                      }}
                    >
                      Edit
                    </button>

                    <button
                      className="delete-btn"
                      onClick={() => handleDelete(p.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4" className="no-data">
                  No products registered
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ProductList;