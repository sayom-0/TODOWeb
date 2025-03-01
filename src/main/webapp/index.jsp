<%@ page import="java.util.List" %>
<%@ page import="org.example.todoweb.TODOItem" %>
<!DOCTYPE html>
<html>
<head>
    <title>TODO List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { width: 300px; margin: auto; }
        ul { list-style: none; padding: 0; }
        li { margin: 5px 0; }
    </style>
</head>
<body>
<div class="container">
    <h2>TODO List</h2>
    <form action="todo" method="post">
        <input type="text" name="description" placeholder="New Task" required>
        <input type="hidden" name="action" value="add">
        <button type="submit">Add</button>
    </form>
    <ul>
        <% List<TODOItem> items = (List<TODOItem>) request.getAttribute("items"); %>
        <% if (items != null) { %>
        <% for (int i = 0; i < items.size(); i++) { %>
        <li>
            <%= items.get(i).getDescription() %>
            <form action="todo" method="post" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="index" value="<%= i %>">
                <button type="submit">Delete</button>
            </form>
        </li>
        <% } %>
        <% } %>
    </ul>
    <form action="todo" method="post">
        <input type="hidden" name="action" value="clear">
        <button type="submit">Clear All</button>
    </form>
</div>
</body>
</html>