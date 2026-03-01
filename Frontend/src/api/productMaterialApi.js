import axios from 'axios';

// URL do .env
const API_URL = process.env.REACT_APP_API_PRODUCT_MATERIALS;

// Criar vínculo entre produto e matéria-prima
export const createProductMaterial = (data) => {
  return axios.post(API_URL, data);
};

// Buscar todos os materiais vinculados a um produto
export const getProductMaterialsByProduct = (productId) => {
  return axios.get(`${API_URL}/product/${productId}`);
};

// Remover vínculo
export const deleteProductMaterial = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};