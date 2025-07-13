import { Box, Heading, Button, Spinner, Alert, Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useAuth } from "../context/auth-context";
import productService from "../api/productService";
import ProductFormFields from "../components/ProductFormFields";

const emptyProduct = {
  name: "",
  description: "",
  price: "",
  category: "",
  status: "",
};

export default function ProductForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { token } = useAuth();
  const [product, setProduct] = useState(emptyProduct);
  const [loading, setLoading] = useState(!!id);
  const [error, setError] = useState("");
  const [formErrors, setFormErrors] = useState({});

  const isEdit = Boolean(id);

  useEffect(() => {
    if (isEdit) {
      const loadProduct = async () => {
        try {
          const data = await productService.getProductById(id, token);
          setProduct(data);
        } catch {
          setError("Error loading product");
        } finally {
          setLoading(false);
        }
      };

      loadProduct();
    }
  }, [id, token, isEdit]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEdit) {
        await productService.updateProduct(id, product, token);
      } else {
        await productService.createProduct(product, token);
      }
      navigate("/products");
    } catch (err) {
      setFormErrors(err.fieldErrors);
      setError("Failed to save product");
    }
  };

  if (loading) return <Spinner />;

  return (
    <Box>
      <Heading mb={6}>{isEdit ? "Edit Product" : "Create Product"}</Heading>
      <form onSubmit={handleSubmit}>
        <ProductFormFields product={product} onChange={handleChange} errors={formErrors}/>
        <Flex justify="space-between" mt={6}>
          <Button onClick={() => navigate("/products")}>Back</Button>
          <Button type="submit" colorScheme="teal" mb={6}>
            {isEdit ? "Update" : "Create"}
          </Button>
        </Flex>
      </form>
      {error && (
        <Alert.Root status="error">
          <Alert.Indicator />
          <Alert.Content>
            <Alert.Title>{error}</Alert.Title>
            <Alert.Description>
              Your form has some errors. Please fix them and try again.
            </Alert.Description>
          </Alert.Content>
        </Alert.Root>
      )}
    </Box>
  );
}
