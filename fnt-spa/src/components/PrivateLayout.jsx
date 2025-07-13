import { Box, Button, Flex } from "@chakra-ui/react";
import { useAuth } from "../context/auth-context";
import { Outlet } from "react-router-dom";

export default function PrivateLayout() {
  const { logout } = useAuth();

  return (
    <Box>
      <Flex justify="flex-end" mb={6}>
        <Button colorScheme="red" onClick={logout}>
          Logout
        </Button>
      </Flex>
      <Outlet />
    </Box>
  );
}
