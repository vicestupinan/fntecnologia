import {
  Box,
  Heading,
  SimpleGrid,
  Text,
  Spinner,
  Alert,
  Button,
  Flex,
  HStack,
} from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useAuth } from "../context/auth-context";
import { getAllProducts, deleteProduct } from "../api/productService";
import { useNavigate } from "react-router-dom";

function ProductList() {
  const { token } = useAuth();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      setError("");
      try {
        const data = await getAllProducts(token);
        setProducts(data);
      } catch (err) {
        console.log(err.message);
        setError(err.message || "Failed to fetch products");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [token]);

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this product?"))
      return;

    try {
      await deleteProduct(id, token);
      setProducts((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      setError(err.message || "Failed to delete product");
    }
  };

  return (
    <Box>
      <Heading pb={4} textAlign="center">
        Product List
      </Heading>
      <Flex justify="space-between" align="center" mb={6}>
        <Heading>Product List</Heading>
        <Flex gap={4}>
          <Button colorScheme="blue" onClick={() => navigate("/products/new")}>
            Create Product
          </Button>
        </Flex>
      </Flex>

      {loading && <Spinner size="xl" />}

      {error && (
        <Alert status="error" mb={4}>
          {error}
        </Alert>
      )}

      {!loading && !error && (
        <SimpleGrid columns={[1, 2, 3]} gap={4}>
          {products.map((product) => (
            <Box
              key={product.id}
              p={4}
              bg="gray.700"
              borderRadius="lg"
              boxShadow="md"
            >
              <Heading size="md" mb={2}>
                {product.name}
              </Heading>
              <Text mb={2}>{product.description}</Text>
              <Text fontWeight="bold">${product.price}</Text>
              <Text fontSize="sm" color="gray.300">
                Category: {product.category}
              </Text>
              <Text fontSize="sm" color="gray.300" pb={2}>
                Status: {product.status}
              </Text>
              <HStack justify="flex-end" spacing={4}>
                <Button
                  size="sm"
                  onClick={() => navigate(`/products/${product.id}/edit`)}
                >
                  Edit
                </Button>
                <Button
                  size="sm"
                  colorPalette="red"
                  onClick={() => handleDelete(product.id)}
                >
                  Delete
                </Button>
              </HStack>
            </Box>
          ))}
        </SimpleGrid>
      )}
    </Box>
  );
}

export default ProductList;
