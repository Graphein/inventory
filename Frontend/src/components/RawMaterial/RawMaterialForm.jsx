import React, { useState, useEffect } from 'react';
import "../css/RawMaterialForm.css";

const RawMaterialForm = ({ rawMaterial, onSave, onClose }) => {
  const [name, setName] = useState('');
  const [stockQuantity, setQuantity] = useState('');

  useEffect(() => {
    if (rawMaterial) {
      setName(rawMaterial.name);
      setQuantity(rawMaterial.stockQuantity || 0);
    }
  }, [rawMaterial]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({ ...rawMaterial, name, stockQuantity: parseInt(stockQuantity) });
  };

  return (
  <div className="rm-overlay">
    <div className="rm-card">
      <form onSubmit={handleSubmit} className="rm-form">
        <h2 className="rm-title">
          {rawMaterial ? "Edit Material" : "Create Material"}
        </h2>

        <div className="rm-group">
          <label>Name</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div className="rm-group">
          <label>Stock Quantity</label>
          <input
            type="number"
            value={stockQuantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
          />
        </div>

        <div className="rm-actions">
          <button type="submit" className="rm-save">
            Save
          </button>
          <button type="button" onClick={onClose} className="rm-cancel">
            Cancel
          </button>
        </div>
      </form>
    </div>
  </div>
  );
};

export default RawMaterialForm;