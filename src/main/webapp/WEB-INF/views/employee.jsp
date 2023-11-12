<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- Add this line -->
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
    <!-- Add any CSS styles or external CSS links here -->
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h1 {
            text-align: center;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
${registrationSuccess}
    <h1>Employee List</h1>
    <table>
        <tr>
            <th>Employee ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Salary</th>
            <th>Photo</th>
        </tr>
        <!-- Iterate through the list of employees and display them in the table -->
        <c:forEach var="employee" items="${listEmployeeDTO}">
            <tr>
                <td>${employee.employeeId}</td>
                <td>${employee.employeeName}</td>
                <td>${employee.emailId}</td>
                <td>${employee.salary}</td>
                <td><img src="data:image/jpg;base64,${employee.photo}" alt="Employee Photo" width="100px"></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>