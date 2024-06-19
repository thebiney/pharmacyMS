CREATE TABLE customers (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    drug_code VARCHAR(10),
    customer_id VARCHAR(10),
    date DATE,
    quantity INT,
    FOREIGN KEY (drug_code) REFERENCES drugs(code),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE drug_suppliers (
    drug_code VARCHAR(10),
    supplier_id VARCHAR(10),
    PRIMARY KEY (drug_code, supplier_id),
    FOREIGN KEY (drug_code) REFERENCES drugs(code),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);
