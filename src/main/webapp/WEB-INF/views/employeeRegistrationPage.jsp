<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Registration</title>
    <!-- Add any CSS styles or external CSS links here -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }
        label {
            display: block;
            font-weight: bold;
            margin-top: 10px;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 3px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Employee Registration</h1>
    <div class="container">
        <form action="/api/v4/employee" method="post" enctype="multipart/form-data">
            <label for="employeeName">Employee Name:</label>
            <input type="text" id="employeeName" name="employeeName" required>

            <label for="emailId">Email:</label>
            <input type="email" id="emailId" name="emailId" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <label for="salary">Salary:</label>
            <input type="number" id="salary" name="salary" required>

            <!-- Add a file input for employee photo -->
            <label for="photo">Employee Photo:</label>
            <input type="file" id="file" name="file" accept="image/*">

            <input type="submit" value="Register">
        </form>
    </div>
</body>
</html>