import axios from "axios";

const API_URL = "http://localhost:8080/api/auth";

const login = async (email, password) => {
  try {
    const response = await axios.post(`${API_URL}/login`, {
      email,
      password,
    });
    return response.data.token;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Login failed");
  }
};

export default {
  login,
};
