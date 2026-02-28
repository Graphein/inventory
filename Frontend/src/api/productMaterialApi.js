import axios from 'axios';

const API_URL = 'http://localhost:8080/api/product-materials';

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