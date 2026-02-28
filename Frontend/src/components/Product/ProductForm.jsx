import React, { useState, useEffect } from 'react';
import { getAllRawMaterials } from '../../api/rawMaterialApi';
import "../css/ProductForm.css";
import {
  createProductMaterial,
  getProductMaterialsByProduct,
  deleteProductMaterial
} from '../../api/productMaterialApi';

const ProductForm = ({ product, onSave, onClose }) => {

  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');

  const [rawMaterials, setRawMaterials] = useState([]);
  const [selectedMaterial, setSelectedMaterial] = useState('');
  const [materialQuantity, setMaterialQuantity] = useState('');
  const [linkedMaterials, setLinkedMaterials] = useState([]);

  // Carrega dados do produto ao editar
  useEffect(() => {
    if (product) {
      setName(product.name || '');
      setPrice(product.price || '');
      setQuantity(product.quantity || 0);

      if (product.id) {
        loadLinkedMaterials(product.id);
      }
    } else {
      resetForm();
    }
  }, [product]);

  // Carrega todos os materiais
  useEffect(() => {
    loadRawMaterials();
  }, []);

  const loadRawMaterials = async () => {
    const res = await getAllRawMaterials();
    setRawMaterials(res.data);
  };

  const loadLinkedMaterials = async (productId) => {
    if (!productId) return;
    const res = await getProductMaterialsByProduct(productId);
    setLinkedMaterials(res.data);
  };

  const resetForm = () => {
    setName('');
    setPrice('');
    setQuantity('');
    setLinkedMaterials([]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    onSave({
      ...product,
      name,
      price: parseFloat(price),
      quantity: parseInt(quantity)
    });
  };

  const handleAddMaterial = async () => {
    if (!selectedMaterial || !materialQuantity) {
      alert("Select material and quantity!");
      return;
    }

    if (!product?.id) {
      alert("Save the product first!");
      return;
    }

    await createProductMaterial({
      productId: product.id,
      rawMaterialId: parseInt(selectedMaterial),
      quantity: parseFloat(materialQuantity)
    });

    setSelectedMaterial('');
    setMaterialQuantity('');
    loadLinkedMaterials(product.id);
  };

  const handleDeleteMaterial = async (id) => {
    await deleteProductMaterial(id);
    loadLinkedMaterials(product.id);
  };
  return (
  <div className="pf-overlay">
    <div className="pf-card">
      <form onSubmit={handleSubmit} className="pf-form">
        <h2 className="pf-title">
          {product ? "Edit Product" : "Create Product"}
        </h2>

        <div className="pf-group">
          <label>Name</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div className="pf-group">
          <label>Price</label>
          <input
            type="number"
            step="0.01"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            required
          />
        </div>

        <div className="pf-group">
          <label>Quantity</label>
          <input
            type="number"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
          />
        </div>

        <div className="pf-actions">
          <button type="submit" className="pf-save">
            Save
          </button>
          <button type="button" onClick={onClose} className="pf-cancel">
            Cancel
          </button>
        </div>

        {product?.id && (
          <div className="pf-materials">
            <h3>Materials</h3>

            <div className="pf-material-inputs">
              <select
                value={selectedMaterial}
                onChange={(e) => setSelectedMaterial(e.target.value)}
              >
                <option value="">Select material</option>
                {rawMaterials.map((m) => (
                  <option key={m.id} value={m.id}>
                    {m.name}
                  </option>
                ))}
              </select>

              <input
                type="number"
                step="0.01"
                placeholder="Quantity required"
                value={materialQuantity}
                onChange={(e) => setMaterialQuantity(e.target.value)}
              />

              <button
                type="button"
                onClick={handleAddMaterial}
                className="pf-add"
              >
                Add
              </button>
            </div>

            <div className="pf-material-list">
              {linkedMaterials.map((m) => (
                <div key={m.id} className="pf-material-item">
                  <span>
                    Material ID: {m.rawMaterialId} - Qty: {m.quantity}
                  </span>
                  <button
                    type="button"
                    onClick={() => handleDeleteMaterial(m.id)}
                    className="pf-remove"
                  >
                    Remove
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}
      </form>
    </div>
  </div>
  );
};

export default ProductForm;