import React, { useState, useEffect } from 'react';
import { getAllRawMaterials } from '../../api/rawMaterialApi';
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
    <div className="modal">
      <form onSubmit={handleSubmit}>
        <h2>{product ? 'Edit Product' : 'Create Product'}</h2>

        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        <input
          type="number"
          step="0.01"
          placeholder="Price"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
          required
        />

        <input
          type="number"
          placeholder="Quantity"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
        />

        <button type="submit">Save</button>

        {/* Materials Section */}
        {product?.id && (
          <>
            <h3>Materials</h3>

            <select
              value={selectedMaterial}
              onChange={(e) => setSelectedMaterial(e.target.value)}
            >
              <option value="">Select material</option>
              {rawMaterials.map(m => (
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

            <button type="button" onClick={handleAddMaterial}>
              Add Material
            </button>

            <ul>
              {linkedMaterials.map(m => (
                <li key={m.id}>
                  Material ID: {m.rawMaterialId} - Qty: {m.quantity}
                  <button
                    type="button"
                    onClick={() => handleDeleteMaterial(m.id)}
                  >
                    Remove
                  </button>
                </li>
              ))}
            </ul>
          </>
        )}

        <button type="button" onClick={onClose}>Cancel</button>
      </form>
    </div>
  );
};

export default ProductForm;