import { Box, Field, Heading, Input, Button } from "@chakra-ui/react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/auth-context";
import authService from "../api/authService";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [formError, setFormError] = useState("");

  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormError("");

    try {
      const token = await authService.login(email, password);
      login(token);
      navigate("/products");
    } catch (err) {
      console.log(err.message);
      setFormError(err.message || "Login failed");
    }
  };

  return (
    <Box bg="gray.800" p={8} borderRadius="lg">
      <Heading mb={6} textAlign="center">Login Form</Heading>
      <form onSubmit={handleSubmit}>
        <Field.Root mb={4}>
          <Field.Label>Email</Field.Label>
          <Input
            placeholder="Enter your email"
            borderColor="cyan.500"
            type="email"
            onChange={(e) => setEmail(e.target.value)}
          />
          <Field.ErrorText></Field.ErrorText>
        </Field.Root>
        <Field.Root invalid={!!formError} mb={4}>
          <Field.Label>Password</Field.Label>
          <Input
            placeholder="Enter your password"
            borderColor="cyan.400"
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />
          <Field.ErrorText>{formError}</Field.ErrorText>
        </Field.Root>
        <Button
          type="submit"
          width="full"
        >
          Login
        </Button>
      </form>
    </Box>
  );
}

export default LoginPage;
