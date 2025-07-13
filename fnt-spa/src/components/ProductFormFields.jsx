import { Box, Field, Input, NativeSelect } from "@chakra-ui/react";

export default function ProductFormFields({ product, onChange, errors }) {
  return (
    <Box>
      <Field.Root mb={4} invalid={!!errors.name}>
        <Field.Label>Name</Field.Label>
        <Input
          name="name"
          value={product.name}
          onChange={onChange}
          placeholder="Product name"
        />
        {errors.name && <Field.ErrorText>{errors.name[0]}</Field.ErrorText>}
      </Field.Root>

      <Field.Root mb={4} invalid={!!errors.description}>
        <Field.Label>Description</Field.Label>
        <Input
          name="description"
          value={product.description}
          onChange={onChange}
          placeholder="Product description"
        />
        {errors.description && (
          <Field.ErrorText>{errors.description[0]}</Field.ErrorText>
        )}
      </Field.Root>

      <Field.Root mb={4} invalid={!!errors.price}>
        <Field.Label>Price</Field.Label>
        <Input
          type="number"
          name="price"
          value={product.price}
          onChange={onChange}
          placeholder="Product price"
        />
        {errors.price && <Field.ErrorText>{errors.price[0]}</Field.ErrorText>}
      </Field.Root>

      <Field.Root mb={4} invalid={!!errors.category}>
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
        {errors.category && (
          <Field.ErrorText>{errors.category[0]}</Field.ErrorText>
        )}
      </Field.Root>

      <Field.Root mb={4} invalid={!!errors.status}>
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
        {errors.status && <Field.ErrorText>{errors.status[0]}</Field.ErrorText>}
      </Field.Root>
    </Box>
  );
}
