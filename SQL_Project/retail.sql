CREATE DATABASE virtusa_db;
USE virtusa_db;

SHOW TABLES;

CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(50),
    city VARCHAR(50)
);

CREATE TABLE Products (
    product_id INT PRIMARY KEY,
    name VARCHAR(50),
    category VARCHAR(50),
    price INT
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Order_Items (
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

INSERT INTO Customers VALUES
(1, 'Renu', 'Hyderabad'),
(2, 'Rahul', 'Bangalore'),
(3, 'Sneha', 'Chennai'),
(4, 'Arjun', 'Delhi'),
(5, 'Meena', 'Mumbai');

INSERT INTO Products VALUES
(1, 'Laptop', 'Electronics', 50000),
(2, 'Phone', 'Electronics', 20000),
(3, 'Shoes', 'Fashion', 3000),
(4, 'Watch', 'Fashion', 5000),
(5, 'Book', 'Education', 500);

INSERT INTO Orders VALUES
(101, 1, '2026-01-10'),
(102, 2, '2026-02-15'),
(103, 1, '2026-03-20'),
(104, 3, '2026-03-25'),
(105, 4, '2026-04-05');

INSERT INTO Order_Items VALUES
(101, 1, 1),
(101, 5, 2),
(102, 2, 1),
(103, 3, 2),
(104, 4, 1),
(105, 1, 1);

SELECT * FROM Customers;
SELECT * FROM Products;
SELECT * FROM Orders;
SELECT * FROM Order_Items;

-- 1. Top-selling products
SELECT p.name, SUM(oi.quantity) AS total_sold
FROM Order_Items oi
JOIN Products p ON oi.product_id = p.product_id
GROUP BY p.name
ORDER BY total_sold DESC;

-- 2. Most valuable customers
SELECT c.name, SUM(p.price * oi.quantity) AS total_spent
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
JOIN Order_Items oi ON o.order_id = oi.order_id
JOIN Products p ON oi.product_id = p.product_id
GROUP BY c.name
ORDER BY total_spent DESC;

-- 3. Monthly revenue
SELECT DATE_FORMAT(o.date, '%Y-%m') AS month,
       SUM(p.price * oi.quantity) AS revenue
FROM Orders o
JOIN Order_Items oi ON o.order_id = oi.order_id
JOIN Products p ON oi.product_id = p.product_id
GROUP BY month;

-- 4. Category-wise sales
SELECT p.category, SUM(p.price * oi.quantity) AS total_sales
FROM Products p
JOIN Order_Items oi ON p.product_id = oi.product_id
GROUP BY p.category;

-- 5. Inactive customers (no orders)
SELECT c.name
FROM Customers c
LEFT JOIN Orders o ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL;