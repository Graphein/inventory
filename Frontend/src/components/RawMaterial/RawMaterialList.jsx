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
  <div className="raw-container">
    <h2 className="raw-title">Raw Materials</h2>

    <button
      className="create-button"
      onClick={() => {
        setEditingMaterial(null);
        setShowForm(true);
      }}
    >
      + Create Material
    </button>

    {showForm && (
      <RawMaterialForm
        rawMaterial={editingMaterial}
        onSave={handleSave}
        onClose={() => {
          setShowForm(false);
          setEditingMaterial(null);
        }}
      />
    )}

    <div className="table-wrapper">
      <table className="raw-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Stock</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {materials.length > 0 ? (
            materials.map((m) => (
              <tr key={m.id}>
                <td>{m.name}</td>
                <td>{m.stockQuantity}</td>
                <td className="actions">
                  <button
                    className="edit-btn"
                    onClick={() => {
                      setEditingMaterial(m);
                      setShowForm(true);
                    }}
                  >
                    Edit
                  </button>

                  <button
                    className="delete-btn"
                    onClick={() => handleDelete(m.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3" className="no-data">
                No materials registered
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  </div>
  );
};

export default RawMaterialList;