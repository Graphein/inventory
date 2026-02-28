import React, { useState, useEffect } from 'react';
import { getAllRawMaterials, deleteRawMaterial, createRawMaterial, updateRawMaterial } from '../../api/rawMaterialApi';
import RawMaterialForm from './RawMaterialForm';

const RawMaterialList = () => {
  const [materials, setMaterials] = useState([]);
  const [editingMaterial, setEditingMaterial] = useState(null);
  const [showForm, setShowForm] = useState(false);

  const fetchMaterials = () => {
    getAllRawMaterials().then(res => setMaterials(res.data));
  };

  useEffect(() => {
    fetchMaterials();
  }, []);

  const handleDelete = (id) => {
    deleteRawMaterial(id).then(() => fetchMaterials());
  };

  const handleSave = (material) => {
    const request = material.id ? updateRawMaterial(material.id, material) : createRawMaterial(material);
    request.then(() => {
      fetchMaterials();
      setShowForm(false);
      setEditingMaterial(null);
    });
  };

  return (
    <div>
      <h1>Raw Materials</h1>
      <button onClick={() => { setEditingMaterial(null); setShowForm(true); }}>Create Material</button>
      {showForm && (
        <RawMaterialForm
          rawMaterial={editingMaterial}
          onSave={handleSave}
          onClose={() => { setShowForm(false); setEditingMaterial(null); }}
        />
      )}
      <table>
        <thead>
          <tr>
            <th>Name</th><th>Quantity</th><th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {materials.map(m => (
            <tr key={m.id}>
              <td>{m.name}</td>
              <td>{m.stockQuantity}</td>
              <td>
                <button onClick={() => { setEditingMaterial(m); setShowForm(true); }}>Edit</button>
                <button onClick={() => handleDelete(m.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RawMaterialList;