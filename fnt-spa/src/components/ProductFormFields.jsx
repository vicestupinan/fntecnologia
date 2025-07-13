import { Box, Field, Input, NativeSelect } from "@chakra-ui/react";

export default function ProductFormFields({ product, onChange }) {
  return (
    <Box>
      <Field.Root mb={4}>
        <Field.Label>Name</Field.Label>
        <Input
          name="name"
          value={product.name}
          onChange={onChange}
          placeholder="Product name"
        />
      </Field.Root>

      <Field.Root mb={4}>
        <Field.Label>Description</Field.Label>
        <Input
          name="description"
          value={product.description}
          onChange={onChange}
          placeholder="Product description"
        />
      </Field.Root>

      <Field.Root mb={4}>
        <Field.Label>Price</Field.Label>
        <Input
          type="number"
          name="price"
          value={product.price}
          onChange={onChange}
          placeholder="Product price"
        />
      </Field.Root>

      <Field.Root mb={4}>
        <Field.Label>Category</Field.Label>
        <NativeSelect.Root>
          <NativeSelect.Field
            name="category"
            placeholder="Select category"
            value={product.category}
            onChange={onChange}
          >
            <option value="ELECTRONICS">Electronics</option>
            <option value="FASHION">Fashion</option>
            <option value="HOME">Home</option>
            <option value="SPORTS">Sports</option>
            <option value="TOYS">Toys</option>
          </NativeSelect.Field>
          <NativeSelect.Indicator />
        </NativeSelect.Root>
      </Field.Root>

      <Field.Root mb={4}>
        <Field.Label>Status</Field.Label>
        <NativeSelect.Root>
          <NativeSelect.Field
            name="status"
            placeholder="Select status"
            value={product.status}
            onChange={onChange}
          >
            <option value="AVAILABLE">Available</option>
            <option value="UNAVAILABLE">Unavailable</option>
          </NativeSelect.Field>
          <NativeSelect.Indicator />
        </NativeSelect.Root>
        
      </Field.Root>
    </Box>
  );
}
