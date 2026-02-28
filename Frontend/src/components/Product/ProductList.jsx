import React, { useState, useEffect } from 'react';
import { getAllProducts, deleteProduct, createProduct, updateProduct } from '../../api/productApi';
import ProductForm from './ProductForm';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [editingProduct, setEditingProduct] = useState(null);
  const [showForm, setShowForm] = useState(false);

  const fetchProducts = () => {
    getAllProducts().then(res => setProducts(res.data));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleDelete = (id) => {
    deleteProduct(id).then(() => fetchProducts());
  };

  const handleSave = (product) => {
    const request = product.id ? updateProduct(product.id, product) : createProduct(product);
    request.then(() => {
      fetchProducts();
      setShowForm(false);
      setEditingProduct(null);
    });
  };

  return (
    <div>
      <h1>Products</h1>
      <button onClick={() => { setEditingProduct(null); setShowForm(true); }}>Create Product</button>
      {showForm && (
        <ProductForm
          product={editingProduct}
          onSave={handleSave}
          onClose={() => { setShowForm(false); setEditingProduct(null); }}
        />
      )}
      <table>
        <thead>
          <tr>
            <th>Name</th><th>Price</th><th>Quantity</th><th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map(p => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>{p.price}</td>
              <td>{p.quantity}</td>
              <td>
                <button onClick={() => { setEditingProduct(p); setShowForm(true); }}>Edit</button>
                <button onClick={() => handleDelete(p.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductList;