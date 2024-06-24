# pharmacyMS - Pharmacy Management System

## Description

The Pharmacy Management System is a Java-based application that allows users to manage a pharmacy's drug inventory, search for drugs, view all drugs and their suppliers, and view the purchase history for a specific drug.

## Features

1. **Add Drug**: Allows the user to add a new drug to the pharmacy's inventory by providing the drug code, name, description, price, dosage, and supplier ID.
2. **Search for a Drug**: Allows the user to search for a drug by its code or name and displays the drug's details.
3. **View All Drugs and Suppliers**: Displays a list of all drugs in the pharmacy's inventory along with their respective suppliers and locations.
4. **View Purchase History for a Drug**: Allows the user to view the cumulative purchase history for a specific drug, including the purchase date, buyer, and total quantity bought.

## Requirements

- Java 8 or higher
- MySQL database server
- MySQL JDBC driver

## Installation

1. Clone the repository or download the source code.
2. Set up a MySQL database named `pharmacy_database` with the necessary tables (drugs, suppliers, purchase_history).
3. Update the database connection details in the `PharmacyManagementSystem` class with your MySQL server's URL, username, and password.
4. Compile and run the `PharmacyManagementSystem` class.

## Usage

1. Run the `PharmacyManagementSystem` class.
2. Choose an option from the menu:
   - **Add Drug**: Prompts the user to enter the drug code, name, description, price, dosage, and supplier ID, then adds the drug to the database.
   - **Search for a Drug**: Prompts the user to enter a drug code or name, then displays the details of the matching drug(s).
   - **View All Drugs and Suppliers**: Displays a list of all drugs in the pharmacy's inventory along with their respective suppliers and locations.
   - **View Purchase History for a Drug**: Prompts the user to enter a drug name or code, then displays the cumulative purchase history for the specified drug.
   - **Exit**: Exits the Pharmacy Management System.

## Database Schema

The Pharmacy Management System uses the following database schema:

- `drugs` table:
  - `id` (int, primary key)
  - `code` (varchar)
  - `name` (varchar)
  - `description` (varchar)
  - `price` (double)
  - `dosage` (varchar)
  - `supplier_id` (int, foreign key referencing `suppliers` table)
- `suppliers` table:
  - `id` (int, primary key)
  - `supplier_name` (varchar)
  - `location` (varchar)
- `purchase_history` table:
  - `id` (int, primary key)
  - `drug_code` (varchar, foreign key referencing `drugs` table)
  - `purchase_date` (date)
  - `buyer` (varchar)
  - `quantity` (int)

## Contributing

If you find any issues or have suggestions for improvements, feel free to create a new issue or submit a pull request.
