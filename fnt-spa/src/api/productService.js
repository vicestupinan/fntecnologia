import axios from "axios";

const API_URL = "http://localhost:8080/api/products";

export const getAllProducts = async (token) => {
  const response = await axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const getProductById = async (id, token) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error) {
    throw new Error(
      error.response?.data?.message || "Failed to retrieve product"
    );
  }
};

export const createProduct = async (product, token) => {
  try {
    const response = await axios.post(API_URL, product, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error) {
    const errors = error.response?.data?.errors;
    if (errors) {
      throw { fieldErrors: errors };
    }
    throw new Error("Failed to create product");
  }
};

export const updateProduct = async (id, product, token) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, product, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error) {
    const errors = error.response?.data?.errors;
    if (errors) {
      throw { fieldErrors: errors };
    }
    throw new Error("Failed to update product");
  }
};

export const deleteProduct = async (id, token) => {
  try {
    const response = await axios.delete(`${API_URL}/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error) {
    throw new Error(
      error.response?.data?.message || "Failed to delete product"
    );
  }
};

export default {
  getAllProducts,
  getProductById,
  createProduct,
  updateProduct,
  deleteProduct,
};
