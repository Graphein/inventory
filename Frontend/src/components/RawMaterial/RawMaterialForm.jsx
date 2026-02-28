import React, { useState, useEffect } from 'react';

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
    <div className="modal">
      <form onSubmit={handleSubmit}>
        <h2>{rawMaterial ? 'Edit Material' : 'Create Material'}</h2>
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Stock Quantity"
          value={stockQuantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
        />
        <button type="submit">Save</button>
        <button type="button" onClick={onClose}>Cancel</button>
      </form>
    </div>
  );
};

export default RawMaterialForm;