import axios from 'axios';

// URL do .env
const API_URL = process.env.REACT_APP_API_RAW_MATERIALS;

export const getAllRawMaterials = () => axios.get(API_URL);
export const getRawMaterialById = (id) => axios.get(`${API_URL}/${id}`);
export const createRawMaterial = (rawMaterial) => axios.post(API_URL, rawMaterial);
export const updateRawMaterial = (id, rawMaterial) => axios.put(`${API_URL}/${id}`, rawMaterial);
export const deleteRawMaterial = (id) => axios.delete(`${API_URL}/${id}`);