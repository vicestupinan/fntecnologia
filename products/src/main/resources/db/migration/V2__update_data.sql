INSERT INTO products (
        name,
        description,
        price,
        category,
        status,
        created_at,
        updated_at,
        created_by,
        updated_by
    )
VALUES (
        'Mechanical Keyboard',
        'RGB backlit mechanical keyboard',
        120.00,
        'PERIPHERALS',
        'AVAILABLE',
        NOW(),
        NOW(),
        'admin@system.com',
        'admin@system.com'
    ),
    (
        '27-inch Monitor',
        '4K UHD display with HDR support',
        350.00,
        'DISPLAY',
        'AVAILABLE',
        NOW(),
        NOW(),
        'admin@system.com',
        'admin@system.com'
    ),
    (
        'External SSD',
        '1TB USB-C SSD drive',
        150.00,
        'STORAGE',
        'AVAILABLE',
        NOW(),
        NOW(),
        'admin@system.com',
        'admin@system.com'
    ),
    (
        'Gaming Mouse',
        'Ergonomic mouse with 6 programmable buttons',
        60.00,
        'PERIPHERALS',
        'AVAILABLE',
        NOW(),
        NOW(),
        'admin@system.com',
        'admin@system.com'
    );