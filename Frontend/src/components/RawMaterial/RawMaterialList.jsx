import React, { useState, useEffect } from 'react';
import { getAllRawMaterials, deleteRawMaterial, createRawMaterial, updateRawMaterial } from '../../api/rawMaterialApi';
import RawMaterialForm from './RawMaterialForm';

const RawMaterialList = () => {
  const [materials, setMaterials] = useState([]);
  const [editingMaterial, setEditingMaterial] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const fetchMaterials = () => {
    getAllRawMaterials().then(res => setMaterials(res.data));
  };

  useEffect(() => {
    fetchMaterials();
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
    deleteRawMaterial(id)
      .then(() => {
        fetchMaterials();
        setErrorMessage("");
      })
      .catch((error) => {
        if (error.response?.status === 409) {
          setErrorMessage(
            "Cannot delete raw material because it is linked to a product."
          );
        } else {
          setErrorMessage("Unexpected error while deleting raw material.");
        }
      });
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
    {errorMessage && (
      <div className="error-message">
        {errorMessage}
      </div>
    )}

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